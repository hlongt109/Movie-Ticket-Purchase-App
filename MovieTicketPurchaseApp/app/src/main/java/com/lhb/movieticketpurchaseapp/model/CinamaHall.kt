package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class CinemaHall(
    val id: String,
    val theaterId: String,
    val name: String,
    val capacity: Int // suc chua
)
data class CinemaHallResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("theaterId") val theaterId: String,
    @SerializedName("name") val name: String,
    @SerializedName("capacity") val capacity: Int
)
fun CinemaHallResponse.toCinemaHall() : CinemaHall{
    return CinemaHall(
        id = this.id,
        theaterId = this.theaterId,
        name = this.name,
        capacity = this.capacity
    )
}
data class CinemaHallFormData(
    var id: String? = "",
    var theaterId: String = "",
    var name: String = "",
    var capacity: Int = 0
)
fun CinemaHall?.toCinemaHallFormData() = this?.let {
    CinemaHallFormData(
        this.id,
        this.theaterId,
        this.name,
        this.capacity
    )
}