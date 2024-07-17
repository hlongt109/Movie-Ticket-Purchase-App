package com.lhb.movieticketpurchaseapp.model
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val genre: String,
    val director: String,
    val duration: Int,
    val releaseDate: String,
    val showTime: String, // khong can
    val rating: Double,
    val poster: String,
    val images: List<String>,
    val trailer: String,
    val status: Int, // 0 coming soon, 1 new , 2 popular
)

data class MovieResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("director") val director: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("showTime") val showTime: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("poster") val poster: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("trailer") val trailer: String,
    @SerializedName("status") val status: Int
)

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.description,
        genre = this.genre,
        director = this.director,
        duration = this.duration,
        releaseDate = this.releaseDate,
        showTime = this.showTime,
        rating = this.rating,
        poster = this.poster,
        images = this.images,
        trailer = this.trailer,
        status = this.status
    )
}

data class MovieFormData(
    var id: String? = "",
    var title: String = "",
    var description: String = "",
    var genre: String = "",
    var director: String = "",
    var duration: Int = 0,
    var releaseDate: String = "",
    var showTime: String = "",
    var rating: Double = 0.0,
    var poster: String = "",
    var images: List<String> = emptyList(),
    var trailer: String = "",
    var status: Int = 0
)
fun Movie?.toFormData() = this?.let {
    MovieFormData(
        this.id,
        this.title,
        this.description,
        this.genre,
        this.director,
        this.duration,
        this.releaseDate,
        this.showTime,
        this.rating,
        this.poster,
        this.images,
        this.trailer,
        this.status
    )
}