package com.lhb.movieticketpurchaseapp.repository

import com.lhb.movieticketpurchaseapp.model.CinemaHallFormData
import com.lhb.movieticketpurchaseapp.model.FoodAndDrinkFormData
import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.model.MovieTypeFormData
import com.lhb.movieticketpurchaseapp.model.ShowTimeFormData
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import com.lhb.movieticketpurchaseapp.model.TheaterFormData
import com.lhb.movieticketpurchaseapp.model.TimeFrameFormData
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
        return retrofitService.managerService.updateMovie(id,mapRequestBody,posterPart, trailerPart, imageParts)
    }
    suspend fun deleteMovie(id: String) = retrofitService.managerService.deleteMovie(id)
    suspend fun getMovieById(id: String) = retrofitService.managerService.getMovieDetails(id)

    // ===== Theater ===========
    suspend fun getAllTheater() = retrofitService.managerService.getAllTheaters()
    suspend fun createTheater(formData: TheaterFormData): Response<StatusResponse> {
        return retrofitService.managerService.createTheater(formData)
    }
    suspend fun updateTheater(id: String,formData: TheaterFormData): Response<StatusResponse> {
        return retrofitService.managerService.updateTheater(id,formData)
    }
    suspend fun deleteTheater(id: String) = retrofitService.managerService.deleteTheater(id)
    suspend fun getTheaterById(id: String) = retrofitService.managerService.getTheaterDetails(id)

    // ===== CinemaHall ===========

    suspend fun getAllCinemaHall() = retrofitService.managerService.getAllCinemaHall()
    suspend fun addCinemaHall(formData: CinemaHallFormData): Response<StatusResponse>{
        return retrofitService.managerService.addCinemaHall(formData)
    }
    suspend fun updateCinemaHall(id: String, formData: CinemaHallFormData): Response<StatusResponse>{
        return retrofitService.managerService.updateCinemaHall(id,formData)
    }
    suspend fun deleteCinemaHall(id: String) = retrofitService.managerService.deleteCinemaHall(id)
    suspend fun getCinemaHallById(id: String) = retrofitService.managerService.getCinemaHallDetails(id)

    // ===== showTime ===========
    suspend fun getAllShowTime() = retrofitService.managerService.getAllShowTime()
    suspend fun addShowTime(formData: ShowTimeFormData) : Response<StatusResponse>{
        return retrofitService.managerService.addShowTime(formData)
    }
    suspend fun updateShowTime(id: String,formData: ShowTimeFormData) : Response<StatusResponse>{
        return retrofitService.managerService.updateShowTime(id,formData)
    }
    suspend fun deleteShowTime(id:String) = retrofitService.managerService.deleteShowTime(id)
    suspend fun getShowTimeById(id: String) = retrofitService.managerService.getShowTimeById(id)

    // ===== time frame ===========
    suspend fun getAllTimeFrame() = retrofitService.managerService.getAllTimeFrame()
    suspend fun addTimeFrame(formData: TimeFrameFormData): Response<StatusResponse>{
        return retrofitService.managerService.addTimeFrame(formData)
    }
    suspend fun updateTimeFrame(id: String, formData: TimeFrameFormData): Response<StatusResponse>{
        return retrofitService.managerService.updateTimeFrame(id,formData)
    }
    suspend fun deleteTimeFrame(id: String) = retrofitService.managerService.deleteTimeFrame(id)
    suspend fun getDetailsTimeFrame(id: String) = retrofitService.managerService.getAllTimeFrameById(id)

    // ===== food drink ===========
    suspend fun getAllFoodDrinks() = retrofitService.managerService.getAllFoodDrink()
    suspend fun addFoodDrink(formData: FoodAndDrinkFormData): Response<StatusResponse>{
        val mapRequestBody = HashMap<String,RequestBody>().apply {
            put("name", getRequestBody(formData.name))
            put("price", getRequestBody(formData.price))
        }
        val imagePart = getFilePart("image",formData.image)
        return retrofitService.managerService.addFoodDrink(mapRequestBody,imagePart)
    }
    suspend fun updateFoodDrink(id: String, formData: FoodAndDrinkFormData): Response<StatusResponse>{
        val mapRequestBody = HashMap<String,RequestBody>().apply {
            put("name", getRequestBody(formData.name))
            put("price", getRequestBody(formData.price))
        }
        val imagePart = getFilePart("image",formData.image)
        return retrofitService.managerService.updateFoodDrinkDrink(id,mapRequestBody,imagePart)
    }
    suspend fun deleteFoodDrink(id: String) = retrofitService.managerService.deleteFoodDrink(id)
    suspend fun getDetailFoodDrink(id: String) = retrofitService.managerService.getFoodDrinkById(id)
}