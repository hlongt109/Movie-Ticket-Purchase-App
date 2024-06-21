package com.lhb.movieticketpurchaseapp.model

data class Response<T>(
    val status: Int,
    val message: String,
    val data: T,
    val token: String,
    val refreshToken: String
)
