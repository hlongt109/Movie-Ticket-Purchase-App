package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class MovieType(
    val id: String,
    val name: String,
)
data class MovieTypeResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
