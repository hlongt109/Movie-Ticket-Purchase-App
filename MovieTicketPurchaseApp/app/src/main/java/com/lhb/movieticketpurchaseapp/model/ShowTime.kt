package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class ShowTime(
    val id: String,
    val movieId: String,
    val nameMovie: String,
    val theaterId: String,
    val cinemaHallId: String,
    val showTimeDate: String, // chon ngay chieu, khi nguoi dung booking thi chon khung gio chieu cua ngay
    val timeFrameId: List<String>,
    val price: Double
)
data class ShowTimeResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("movieId") val movieId: String,
    @SerializedName("nameMovie") val nameMovie: String,
    @SerializedName("theaterId") val theaterId: String,
    @SerializedName("cinemaHallId") val cinemaHallId: String,
    @SerializedName("showTimeDate") val showTimeDate: String,
    @SerializedName("timeFrameId") val timeFrameId: List<String>,
    @SerializedName("price") val price: Double
)
fun ShowTimeResponse.toShowTime(): ShowTime{
    return ShowTime(
        id = this.id,
        movieId = this.movieId,
        nameMovie = this.nameMovie,
        theaterId = this.theaterId,
        cinemaHallId = this.cinemaHallId,
        showTimeDate = this.showTimeDate,
        timeFrameId = this.timeFrameId,
        price = this.price
    )
}
data class ShowTimeFormData(
    var id: String? = "",
    var movieId: String = "",
    var nameMovie: String = "",
    var theaterId: String = "",
    var cinemaHallId: String = "",
    var showTimeDate: String = "",
    var timeFrameId: List<String> = emptyList(),
    var price: Double = 0.0
)
fun ShowTime?.toFormData() = this?.let {
    ShowTimeFormData(
        this.id,
        this.movieId,
        this.nameMovie,
        this.theaterId,
        this.cinemaHallId,
        this.showTimeDate,
        this.timeFrameId,
        this.price
    )
}