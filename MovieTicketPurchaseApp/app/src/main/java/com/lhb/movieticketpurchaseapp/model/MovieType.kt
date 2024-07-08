package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class MovieType(
    val id: String,
    val name: String,
)
data class MovieTypeResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String
)
data class MovieTypeRequest(
    val id: String ? = null,
    val name: String
)
fun MovieTypeResponse.toMovieType(): MovieType{
    return MovieType(id = this.id, name = this.name)
}
data class MovieTypeFormData(
    var id: String? = "",
    var name: String = "",
)
fun MovieType?.toFormData() = this?.let {
    MovieTypeFormData(
        this.id,
        this.name
    )
}
