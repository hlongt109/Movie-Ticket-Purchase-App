package com.lhb.movieticketpurchaseapp.model

data class Ticket(
    val id: String,
    val qrCode: String,
    val userId: String,
    val bookingId: String,
    val seatId: String,
    val price: Double,
    val expirationDate: String,
    val status: Boolean // da xem or chua xem
)
