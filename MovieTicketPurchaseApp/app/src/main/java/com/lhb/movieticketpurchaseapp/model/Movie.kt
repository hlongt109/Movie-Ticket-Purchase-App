package com.lhb.movieticketpurchaseapp.model

data class Movie(
    val id: String,
    // ....
    val type: String,
    val status: Int, // 0 coming soon, 1 new , 2 normal
    val image: String
)
