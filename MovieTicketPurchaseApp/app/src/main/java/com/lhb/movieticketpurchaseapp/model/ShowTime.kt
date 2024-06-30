package com.lhb.movieticketpurchaseapp.model

data class ShowTime(
    val id: String,
    val movieId: String,
    val cinemaHallId: String,
    val showTimeStart: String,
    val showTimeEnd: String,
    val price: String
)
