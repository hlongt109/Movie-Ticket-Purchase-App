package com.lhb.movieticketpurchaseapp.network

import com.lhb.movieticketpurchaseapp.model.BookingFormData
import com.lhb.movieticketpurchaseapp.model.BookingItem
import com.lhb.movieticketpurchaseapp.model.BookingItemFormData
import com.lhb.movieticketpurchaseapp.model.BookingItemResponse
import com.lhb.movieticketpurchaseapp.model.BookingResponse
import com.lhb.movieticketpurchaseapp.model.FavouriteFormData
import com.lhb.movieticketpurchaseapp.model.FavouriteResponse
import com.lhb.movieticketpurchaseapp.model.LoginRequest
import com.lhb.movieticketpurchaseapp.model.LoginResponse
import com.lhb.movieticketpurchaseapp.model.PaymentFormData
import com.lhb.movieticketpurchaseapp.model.PaymentResponse
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import com.lhb.movieticketpurchaseapp.model.StatusResponse2
import com.lhb.movieticketpurchaseapp.model.TicketFormData
import com.lhb.movieticketpurchaseapp.model.TicketResponse
import com.lhb.movieticketpurchaseapp.model.User
import com.lhb.movieticketpurchaseapp.model.UserFormData
import com.lhb.movieticketpurchaseapp.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface UserService {
  @POST("/api/login")
  suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

  @POST("/api/register-new-account")
  suspend fun registerNewAccount(@Body formData: UserFormData): Response<StatusResponse>

  @PUT("/api/update-personal-information/{id}")
  suspend fun updatePersonalInformation(
    @Path("id") id:String,
    @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
    @Part avatar: MultipartBody.Part?
  ): Response<StatusResponse>

  @DELETE("/api/delete-account/{id}")
  suspend fun deleteAccount(@Path("id") id: String): Response<StatusResponse>

  @GET("/api/get-infomation-details/{id}")
  suspend fun getAccountDetails(@Path("id") id: String): Response<UserResponse>

  // booking
  @POST("/api/add-booking")
  suspend fun addBooking(@Body booking: BookingFormData): Response<StatusResponse2>

  @PUT("/api/update-booking/{id}")
  suspend fun updateBooking(
    @Path("id") id: String,
    @Body booking: BookingFormData
  ): Response<StatusResponse>

  @DELETE("/api/delete-booking/{id}")
  suspend fun deleteBooking(@Path("id") id: String): Response<StatusResponse>

  @GET("/api/get-booking-list")
  suspend fun getAllBooking() : Response<List<BookingResponse>>

  @GET("/api/get-booking-details/{id}")
  suspend fun getBookingById(@Path("id") id: String): Response<BookingResponse>

  //bookingItem
  @POST("/api/add-booking-item")
  suspend fun addBookingItem(@Body bookingItem: BookingItemFormData): Response<StatusResponse>

  @PUT("/api/update-booking-item/{id}")
  suspend fun updateBookingItem(
    @Path("id") id: String,
    @Body bookingItem: BookingItemFormData
  ): Response<StatusResponse>

  @DELETE("/api/delete-booking-item/{id}")
  suspend fun deleteBookingItem(@Path("id")id: String): Response<StatusResponse>

  @GET("/api/get-booking-item-list")
  suspend fun getAllBookingItem(): Response<List<BookingItemResponse>>

  @GET("/api/get-booking-item-details/{id}")
  suspend fun getBookingItemById(@Path("id") id: String): Response<BookingItemResponse>

  // payment
  @POST("/api/add-payment")
  suspend fun createPayment(@Body payment: PaymentFormData): Response<StatusResponse2>

  @PUT("/api/update-payment/{id}")
  suspend fun updatePayment(
    @Path("id") id: String,
    @Body payment: PaymentFormData
  ): Response<StatusResponse>

  @DELETE("/api/delete-payment/{id}")
  suspend fun deletePayment(@Path("id")id: String): Response<StatusResponse>

  @GET("/api/get-payment-list")
  suspend fun getAllPayment(): Response<List<PaymentResponse>>
  @GET("/api/get-payment-details/{id}")
  suspend fun getAllPaymentById(@Path("id") id: String): Response<PaymentResponse>

  // ticket
  @POST("/api/add-ticket")
  suspend fun createTicket(@Body ticket: TicketFormData): Response<StatusResponse>

  @PUT("/api/update-ticket/{id}")
  suspend fun updateTicket(
    @Path("id")id: String,
    @Body ticket: TicketFormData
  ): Response<StatusResponse>

  @DELETE("/api/delete-ticket/{id}")
  suspend fun deleteTicket(@Path("id")id: String): Response<StatusResponse>

  @GET("/api/get-ticket-list")
  suspend fun getAllTicket(): Response<List<TicketResponse>>

  @GET("/api/get-ticket-details/{id}")
  suspend fun getTicketById(@Path("id")id: String): Response<TicketResponse>

  @GET("/api/get-ticket-by-code/{ticketCode}")
  suspend fun getTicketByCode(@Path("ticketCode")ticketCode: String): Response<TicketResponse>

  // favourite
  @GET("/api/get-favourite-list")
  suspend fun getAllFavourite(): Response<List<FavouriteResponse>>

  @POST("/api/add-favourite")
  suspend fun addFavourite(@Body favourite: FavouriteFormData): Response<StatusResponse>

  @DELETE("/api/delete-favourite/{id}")
  suspend fun removeFavourite(@Path("id") id: String): Response<StatusResponse>

  // user
  @GET("/api/get-all-user")
  suspend fun getAllUsers(): Response<List<UserResponse>>

  @GET("/api/get-infomation-details/{id}")
  suspend fun getAUser(@Path("id")id: String) : Response<UserResponse>

  @POST("/api/add-user")
  suspend fun addNewUser(
    @Body user: UserFormData
  ): Response<StatusResponse>

  @PUT("/api/update-user/{id}")
  suspend fun updateUser(
    @Path("id") id: String,
    @Body user: UserFormData
  ): Response<StatusResponse>

  @DELETE("/api/delete-account/{id}")
  suspend fun removeUser(@Path("id") id: String) : Response<StatusResponse>
}