package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class Theater(
    val id: String,
    val name: String,
    val location: String,
    val contact: String
)
data class TheaterResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("contact") val contact: String
)
fun TheaterResponse.toTheater() : Theater{
    return Theater(
        id = this.id,
        name = this.name,
        location = this.location,
        contact = this.contact
    )
}
data class TheaterFormData(
    var id: String? = "",
    var name: String = "",
    var location: String = "",
    var contact: String = "",
)
fun Theater?.toFormData() = this?.let {
    TheaterFormData(
        this.id,
        this.name,
        this.location,
        this.contact
    )
}

