package com.lhb.movieticketpurchaseapp.repository

import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.model.MovieTypeFormData
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import com.lhb.movieticketpurchaseapp.network.ManagerService
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.utils.getFilePart
import com.lhb.movieticketpurchaseapp.utils.getFileParts
import com.lhb.movieticketpurchaseapp.utils.getRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class ManagerRepository(private val retrofitService: RetrofitService) {
    // ===== movie genre ================
    suspend fun getAllMovieGenre() = retrofitService.managerService.getMovieTypeList()
    suspend fun createMovieGenre(formData: MovieTypeFormData): Response<StatusResponse> {
        return retrofitService.managerService.createMovieType(formData)
    }
    suspend fun updateMovieGenre(id: String,formData: MovieTypeFormData): Response<StatusResponse> {
        return retrofitService.managerService.updateMovieType(id,formData)
    }
    suspend fun deleteMovieGenre(id: String) = retrofitService.managerService.deleteMovieType(id)
    suspend fun getMovieGenreById(id: String) = retrofitService.managerService.getMovieTypeDetails(id)

    // ===== Movie ================
    suspend fun getAllMovies() = retrofitService.managerService.getMovieList()
    suspend fun createMovie(formData: MovieFormData) : Response<StatusResponse>{
        val mapRequestBody = HashMap<String, RequestBody>().apply {
            put("title", getRequestBody(formData.title))
            put("description", getRequestBody(formData.description))
            put("genre", getRequestBody(formData.genre))
            put("director", getRequestBody(formData.director))
            put("duration", getRequestBody(formData.duration))
            put("releaseDate", getRequestBody(formData.releaseDate))
            put("showTime", getRequestBody(formData.showTime))
            put("rating", getRequestBody(formData.rating))
            put("status", getRequestBody(formData.status))
        }
        val posterPart = getFilePart("poster", formData.poster)
        val trailerPart = getFilePart("trailer", formData.trailer)
        val imageParts = getFileParts("images", formData.images)
        return retrofitService.managerService.createMovie(mapRequestBody,posterPart,trailerPart,imageParts)
    }
    suspend fun updateMovie(id: String, formData: MovieFormData) : Response<StatusResponse>{
        return retrofitService.managerService.updateMovie(id,formData)
    }
    suspend fun deleteMovie(id: String) = retrofitService.managerService.deleteMovie(id)
    suspend fun getMovieById(id: String) = retrofitService.managerService.getMovieDetails(id)

    // =====
}