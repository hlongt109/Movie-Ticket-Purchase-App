package com.lhb.movieticketpurchaseapp.network

import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.MovieResponse
import com.lhb.movieticketpurchaseapp.model.MovieTypeFormData
import com.lhb.movieticketpurchaseapp.model.MovieTypeResponse
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ManagerService {
    // movie
    @GET("/api/get-movie-list")
    suspend fun getMovieList() : Response<List<MovieResponse>>

    @GET("/api/get-movie-details/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): Response<MovieResponse>

    @Multipart
    @POST("/api/add-movie")
    suspend fun createMovie(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part poster: MultipartBody.Part?,
        @Part trailer: MultipartBody.Part?,
        @Part images: List<MultipartBody.Part>
    ) : Response<StatusResponse>

    @PUT("/api/update-movie/{id}")
    suspend fun updateMovie(
        @Path("id") id: String,
        @Body formData: MovieFormData
    ) : Response<StatusResponse>

    @DELETE("/api/delete-movie/{id}")
    suspend fun deleteMovie(@Path("id") id: String) : Response<StatusResponse>

    // movie type =================================================================
    @GET("/api/get-movie-type-list")
    suspend fun getMovieTypeList() : Response<List<MovieTypeResponse>>
    @GET("/api/get-movie-type-details/{id}")
    suspend fun getMovieTypeDetails(@Path("id") id: String): Response<MovieTypeResponse>

    @POST("/api/add-movie-type")
    suspend fun createMovieType(
        @Body name: MovieTypeFormData
    ) : Response<StatusResponse>

    @PUT("/api/update-movie-type/{id}")
    suspend fun updateMovieType(
        @Path("id") id: String,
        @Body formData: MovieTypeFormData
    ): Response<StatusResponse>
    @DELETE("/api/delete-movie-type/{id}")
    suspend fun deleteMovieType(@Path("id") id: String) : Response<StatusResponse>

    //
}