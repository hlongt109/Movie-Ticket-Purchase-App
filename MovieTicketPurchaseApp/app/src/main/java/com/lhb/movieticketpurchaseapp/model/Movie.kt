package com.lhb.movieticketpurchaseapp.model

import androidx.core.app.NotificationCompat.Action.SemanticAction
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: String,
    val title: String,
    val description: String,
    val genre: String,
    val director: String,
    val duration: Double,
    val releaseDate : String,
    val showTime: String,
    val rating: Double,
    val poster: String,
    val images: List<String>,
    val trailer: String,
    val status: Int, // 0 coming soon, 1 new , 2 normal
)

data class MovieResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("director") val director: String,
    @SerializedName("duration") val duration: Double,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("showTime") val showTime: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("poster") val poster: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("trailer") val trailer: String,
    @SerializedName("status") val status: Int
)