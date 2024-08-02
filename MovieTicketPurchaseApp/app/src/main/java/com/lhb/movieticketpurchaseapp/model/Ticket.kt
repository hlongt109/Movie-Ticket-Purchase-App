package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class Ticket(
    val id: String,
    val ticketCode: String, // đếm tổng số vé trong ngày chieu de nhan vien tim kiem va xac nhan
    val userId: String,
    val bookingId: String,
    val seatId: List<String>,
    val price: Double,
    val expirationDate: String,
    val status: Boolean // da xem or chua xem
)
data class TicketResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("ticketCode") val ticketCode: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("bookingId") val bookingId: String,
    @SerializedName("seatId") val seatId: List<String>,
    @SerializedName("price") val price: Double,
    @SerializedName("expirationDate") val expirationDate: String,
    @SerializedName("status") val status: Boolean
)
fun TicketResponse.toTicket(): Ticket{
    return Ticket(
        id = this.id,
        ticketCode = this.ticketCode,
        userId = this.userId,
        bookingId = this.bookingId,
        seatId =  this.seatId,
        price = this.price,
        expirationDate = this.expirationDate,
        status =  this.status
    )
}
data class TicketFormData(
    var id: String? = "",
    var ticketCode: String = "",
    var userId: String = "",
    var bookingId: String = "",
    var seatId: List<String> = emptyList(),
    var price: Double = 0.0,
    var expirationDate: String = "",
    var status: Boolean = false
)
fun Ticket?.toTicketFormData() = this?.let {
    TicketFormData(
        this.id,
        this.ticketCode,
        this.userId,
        this.bookingId,
        this.seatId,
        this.price,
        this.expirationDate,
        this.status
    )
}
