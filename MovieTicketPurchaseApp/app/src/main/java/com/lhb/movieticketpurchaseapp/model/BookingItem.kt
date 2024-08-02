package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class BookingItem(
    val id: String,
    val bookingId: String,
    val itemId: String,
    val price: Double,
    val quantity: Int
)
data class BookingItemResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("bookingId") val bookingId: String,
    @SerializedName("itemId") val itemId: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int
)
fun BookingItemResponse.toBookingItem(): BookingItem{
    return BookingItem(
        id = this.id,
        bookingId = this.bookingId,
        itemId = this.itemId,
        price = this.price,
        quantity = this.quantity
    )
}
data class BookingItemFormData(
    var id: String? = "",
    var bookingId: String = "",
    var itemId: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0
)
fun BookingItem?.toBookingItemFormData() = this?.let {
    BookingItemFormData(
        this.id,
        this.bookingId,
        this.itemId,
        this.price,
        this.quantity
    )
}