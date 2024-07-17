package com.lhb.movieticketpurchaseapp.network

import com.lhb.movieticketpurchaseapp.model.CinemaHallFormData
import com.lhb.movieticketpurchaseapp.model.CinemaHallResponse
import com.lhb.movieticketpurchaseapp.model.FoodAndDrinkFormData
import com.lhb.movieticketpurchaseapp.model.FoodAndDrinkResponse
import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.MovieResponse
import com.lhb.movieticketpurchaseapp.model.MovieTypeFormData
import com.lhb.movieticketpurchaseapp.model.MovieTypeResponse
import com.lhb.movieticketpurchaseapp.model.ShowTimeFormData
import com.lhb.movieticketpurchaseapp.model.ShowTimeResponse
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import com.lhb.movieticketpurchaseapp.model.TheaterFormData
import com.lhb.movieticketpurchaseapp.model.TheaterResponse
import com.lhb.movieticketpurchaseapp.model.TimeFrameFormData
import com.lhb.movieticketpurchaseapp.model.TimeFrameResponse
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
import retrofit2.http.Query

interface ManagerService {
    // ===== movie ========
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

    @Multipart
    @PUT("/api/update-movie/{id}")
    suspend fun updateMovie(
        @Path("id") id: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part poster: MultipartBody.Part?,
        @Part trailer: MultipartBody.Part?,
        @Part images: List<MultipartBody.Part>
    ) : Response<StatusResponse>

    @DELETE("/api/delete-movie/{id}")
    suspend fun deleteMovie(@Path("id") id: String) : Response<StatusResponse>

    // ===== movie type ==========
    @GET("/api/get-movie-type-list")
    suspend fun getMovieTypeList() : Response<List<MovieTypeResponse>>
    @GET("/api/get-movie-type-details/{id}")
    suspend fun getMovieTypeDetails(@Path("id") id: String): Response<MovieTypeResponse>

    @POST("/api/add-movie-type")
    suspend fun createMovieType(
        @Body movieType: MovieTypeFormData
    ) : Response<StatusResponse>

    @PUT("/api/update-movie-type/{id}")
    suspend fun updateMovieType(
        @Path("id") id: String,
        @Body formData: MovieTypeFormData
    ): Response<StatusResponse>
    @DELETE("/api/delete-movie-type/{id}")
    suspend fun deleteMovieType(@Path("id") id: String) : Response<StatusResponse>

    //===== theater ===========
    @GET("/api/get-theater-list")
    suspend fun getAllTheaters(): Response<List<TheaterResponse>>
    @GET("/api/get-theater-details/{id}")
    suspend fun getTheaterDetails(@Path("id") id: String): Response<TheaterResponse>
    @POST("/api/add-theater")
    suspend fun createTheater(@Body theater: TheaterFormData): Response<StatusResponse>
    @PUT("/api/update-theater/{id}")
    suspend fun updateTheater(
        @Path("id") id: String,
        @Body theater: TheaterFormData
    ): Response<StatusResponse>
    @DELETE("/api/delete-theater/{id}")
    suspend fun deleteTheater(@Path("id") id: String): Response<StatusResponse>

    // ===== cinema hall ==========
    @GET("/api/get-cinemahall-list")
    suspend fun getAllCinemaHall(): Response<List<CinemaHallResponse>>
    @GET("/api/get-cinemahall-details/{id}")
    suspend fun getCinemaHallDetails(@Path("id") id: String): Response<CinemaHallResponse>
    @POST("/api/add-cinemaHall")
    suspend fun addCinemaHall(@Body cinemaHall: CinemaHallFormData): Response<StatusResponse>
    @PUT("/api/update-cinemahall/{id}")
    suspend fun updateCinemaHall(
        @Path("id") id:String,
        @Body cinemaHall:CinemaHallFormData
    ): Response<StatusResponse>
    @DELETE("/api/delete-cinemahall/{id}")
    suspend fun deleteCinemaHall(@Path("id") id:String): Response<StatusResponse>

    // ===== showTime =========
    @GET("/api/get-showtime-list")
    suspend fun getAllShowTime(): Response<List<ShowTimeResponse>>
    @GET("/api/get-showtime-details/{id}")
    suspend fun getShowTimeById(@Path("id") id:String): Response<ShowTimeResponse>
    @POST("/api/add-showtime")
    suspend fun addShowTime(@Body showTime: ShowTimeFormData) : Response<StatusResponse>
    @PUT("/api/update-showtime/{id}")
    suspend fun updateShowTime(
        @Path("id") id:String,
        @Body showTime: ShowTimeFormData
    ): Response<StatusResponse>
    @DELETE("/api/delete-showtime/{id}")
    suspend fun deleteShowTime(@Path("id") id:String): Response<StatusResponse>

    // ===== time frame ==========
    @GET("/api/get-time-frame-list")
    suspend fun getAllTimeFrame(): Response<List<TimeFrameResponse>>
    @GET("/api/get-time-frame-details/{id}")
    suspend fun getAllTimeFrameById(@Path("id") id: String): Response<TimeFrameResponse>
    @POST("/api/add-time-frame")
    suspend fun addTimeFrame(@Body timeFrame: TimeFrameFormData): Response<StatusResponse>
    @PUT("/api/update-time-frame/{id}")
    suspend fun updateTimeFrame(
        @Path("id") id: String,
        @Body timeFrame : TimeFrameFormData
    ): Response<StatusResponse>
    @DELETE("/api/delete-time-frame/{id}")
    suspend fun deleteTimeFrame(@Path("id") id: String) : Response<StatusResponse>

    // ====== food drink ==========
    @GET("/api/get-fooddrink-list")
    suspend fun getAllFoodDrink(): Response<List<FoodAndDrinkResponse>>
    @GET("/api/get-fooddrink-details/{id}")
    suspend fun getFoodDrinkById(@Path("id") id: String): Response<FoodAndDrinkResponse>

    @Multipart
    @POST("/api/add-fooddrink")
    suspend fun addFoodDrink(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): Response<StatusResponse>

    @Multipart
    @PUT("/api/update-fooddrink/{id}")
    suspend fun updateFoodDrinkDrink(
        @Path("id") id: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): Response<StatusResponse>
    @DELETE("/api/delete-fooddrink/{id}")
    suspend fun deleteFoodDrink(@Path("id") id: String): Response<StatusResponse>

    //
}