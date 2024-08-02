package com.lhb.movieticketpurchaseapp.repository

import com.lhb.movieticketpurchaseapp.model.BookingFormData
import com.lhb.movieticketpurchaseapp.model.BookingItem
import com.lhb.movieticketpurchaseapp.model.BookingItemFormData
import com.lhb.movieticketpurchaseapp.model.FavouriteFormData
import com.lhb.movieticketpurchaseapp.model.LoginRequest
import com.lhb.movieticketpurchaseapp.model.PaymentFormData
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import com.lhb.movieticketpurchaseapp.model.StatusResponse2
import com.lhb.movieticketpurchaseapp.model.TicketFormData
import com.lhb.movieticketpurchaseapp.model.User
import com.lhb.movieticketpurchaseapp.model.UserFormData
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.utils.getFilePart
import com.lhb.movieticketpurchaseapp.utils.getRequestBody
import okhttp3.RequestBody
import retrofit2.Response

class UserRepository(private val retrofitService: RetrofitService) {
    suspend fun login(loginRequest: LoginRequest) = retrofitService.userService.login(loginRequest)
    suspend fun signUp(formData: UserFormData): Response<StatusResponse> {
        return  retrofitService.userService.registerNewAccount(formData)
    }
    suspend fun upadateInfomationAcount(id: String, formData: UserFormData): Response<StatusResponse>{
        val mapRequestBody = HashMap<String, RequestBody>().apply {
            put("username", getRequestBody(formData.username))
            put("name", getRequestBody(formData.name))
            put("email", getRequestBody(formData.email))
            put("password", getRequestBody(formData.password))
            put("role", getRequestBody(formData.role))
            put("phoneNumber", getRequestBody(formData.phoneNumber))
        }
        val avatarPart = getFilePart("avatar",formData.avatar)
        return retrofitService.userService.updatePersonalInformation(id,mapRequestBody,avatarPart)
    }
    suspend fun deleteAccount(id: String) = retrofitService.userService.deleteAccount(id)
    suspend fun getInfomationAccDetails(id: String) = retrofitService.userService.getAccountDetails(id)

    // booking
    suspend fun createBooking(formData: BookingFormData): Response<StatusResponse2>{
        return retrofitService.userService.addBooking(formData)
    }
    suspend fun deleteBooking(id: String) = retrofitService.userService.deleteBooking(id)
    suspend fun getAllBooking() = retrofitService.userService.getAllBooking()
    suspend fun getBookingById(id: String) = retrofitService.userService.getBookingById(id)

    // bookingItem
    suspend fun createBookingItem(formData: BookingItemFormData): Response<StatusResponse>{
        return retrofitService.userService.addBookingItem(formData)
    }
    suspend fun updateBookingItem(id: String, formData: BookingItemFormData): Response<StatusResponse>{
        return retrofitService.userService.updateBookingItem(id,formData)
    }
    suspend fun deleteBookingItem(id: String) = retrofitService.userService.deleteBookingItem(id)
    suspend fun getAllBookingItem() = retrofitService.userService.getAllBookingItem()
    suspend fun getBookingItemById(id: String) = retrofitService.userService.getBookingItemById(id)

    // payment
    suspend fun createPayment(formData: PaymentFormData): Response<StatusResponse2>{
        return retrofitService.userService.createPayment(formData)
    }
    suspend fun getAllPayment() = retrofitService.userService.getAllPayment()
    suspend fun getPaymentById(id: String) = retrofitService.userService.getAllPaymentById(id)

    // ticket
    suspend fun createTicket(formData: TicketFormData): Response<StatusResponse>{
        return retrofitService.userService.createTicket(formData)
    }
    suspend fun updateTicket(id:String,formData: TicketFormData): Response<StatusResponse>{
        return retrofitService.userService.updateTicket(id,formData)
    }
    suspend fun getAllTicket() = retrofitService.userService.getAllTicket()
    suspend fun getTicketById(id: String) = retrofitService.userService.getTicketById(id)

    // favourite
    suspend fun getFavouriteList() = retrofitService.userService.getAllFavourite()
    suspend fun addFavourite(favourite: FavouriteFormData): Response<StatusResponse>{
        return retrofitService.userService.addFavourite(favourite)
    }
    suspend fun removeFavourite(id: String) = retrofitService.userService.removeFavourite(id)
}