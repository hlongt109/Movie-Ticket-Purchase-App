package com.lhb.movieticketpurchaseapp.network

import com.lhb.movieticketpurchaseapp.model.MovieResponse
import com.lhb.movieticketpurchaseapp.model.MovieTypeResponse
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ManagerService {
    // movie
    @GET("/api/get-movies")
    suspend fun getMovieList() : Response<List<MovieResponse>>

    @GET("/api/get-details-movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): Response<MovieResponse>

    @Multipart
    @POST("/api/create-movie")
    suspend fun createMovie(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("genre") genre: RequestBody,
        @Part("director") director: RequestBody,
        @Part("duration") duration: RequestBody,
        @Part("releaseDate") releaseDate: RequestBody,
        @Part("showTime") showTime: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part("status") status: RequestBody,
        @Part poster: MultipartBody.Part?,
        @Part images: MultipartBody.Part?,
        @Part trailer: MultipartBody.Part?
    ) : Response<StatusResponse>

    @Multipart
    @PUT("/api/update-movie/{id}")
    suspend fun updateMovie(
        @Path("id") id: String,
        @Part("description") description: RequestBody,
        @Part("genre") genre: RequestBody,
        @Part("director") director: RequestBody,
        @Part("duration") duration: RequestBody,
        @Part("releaseDate") releaseDate: RequestBody,
        @Part("showTime") showTime: RequestBody,
        @Part("rating") rating: RequestBody,
        @Part("status") status: RequestBody,
        @Part poster: MultipartBody.Part?,
        @Part images: MultipartBody.Part?,
        @Part trailer: MultipartBody.Part?
    ) : Response<StatusResponse>

    @DELETE("/api/delete-movie/{id}")
    suspend fun deleteMovie(@Path("id") id: String) : Response<StatusResponse>

    // movie type =================================================================
    @GET("/api/get-movies")
    suspend fun getMovieTypeList() : Response<List<MovieTypeResponse>>
    @GET("/api/get-details-movie/{id}")
    suspend fun getMovieTypeDetails(@Path("id") id: String): Response<MovieTypeResponse>

    @POST("/api/create-movie")
    suspend fun createMovieType(
        @Part("name") name: RequestBody,
    ) : Response<StatusResponse>

    @PUT("/api/update-movie/{id}")
    suspend fun updateMovieType(
        @Path("id") id: String,
        @Part("name") name: RequestBody
    ): Response<StatusResponse>
    @DELETE("/api/delete-movie/{id}")
    suspend fun deleteMovieType(@Path("id") id: String) : Response<StatusResponse>

    //
}