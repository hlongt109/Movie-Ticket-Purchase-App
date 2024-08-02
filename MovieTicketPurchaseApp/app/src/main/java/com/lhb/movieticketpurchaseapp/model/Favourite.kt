package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class Favourite(
    val id: String,
    val userId: String,
    val movieId: String
)
data class FavouriteResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("movieId") val movieId: String
)
fun FavouriteResponse.toFavourite(): Favourite{
    return Favourite(id, userId, movieId)
}
data class FavouriteFormData(
    var id: String? = "",
    var userId: String = "",
    var movieId: String = ""
)
fun Favourite.toFormData()= this?.let {
    Favourite(
        this.id, this.userId, this.movieId
    )
}