package com.lhb.movieticketpurchaseapp.model

data class Booking(
    val id: String,
    val userId: String,
    val showTimeId: String,
    val paymentId: String,
    val bookingDate: String,
    val totalAmount: Double
)
