package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class Booking(
    val id: String,
    val userId: String,
    val showTimeId: String,
    val timeFrameId: String,
    val paymentId: String,
    val bookingDate: String,
    val totalAmount: Double
)
data class BookingResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("showTimeId") val showTimeId: String,
    @SerializedName("timeFrameId") val timeFrameId: String,
    @SerializedName("paymentId") val paymentId: String,
    @SerializedName("bookingDate") val bookingDate: String,
    @SerializedName("totalAmount") val totalAmount: Double
)
fun BookingResponse.toBooking(): Booking{
    return Booking(
        id = this.id,
        userId = this.userId,
        showTimeId = this.showTimeId,
        timeFrameId = this.timeFrameId,
        paymentId = this.paymentId,
        bookingDate = this.bookingDate,
        totalAmount = this.totalAmount
    )
}
data class BookingFormData(
    var id: String? = "",
    var userId: String = "",
    var showTimeId: String = "",
    var timeFrameId: String = "",
    var paymentId: String = "",
    var bookingDate: String = "",
    var totalAmount: Double = 0.0
)
fun Booking?.toBookingFormData() = this?.let {
    BookingFormData(
        this.id,
        this.userId,
        this.showTimeId,
        this.timeFrameId,
        this.paymentId,
        this.bookingDate,
        this.totalAmount
    )
}
