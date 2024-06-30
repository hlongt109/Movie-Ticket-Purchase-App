package com.lhb.movieticketpurchaseapp.model

data class Payment(
    val id: String,
    val method: String,
    val status: Boolean, // 0 or 1
    val transactionDate: String // day to payment
)
