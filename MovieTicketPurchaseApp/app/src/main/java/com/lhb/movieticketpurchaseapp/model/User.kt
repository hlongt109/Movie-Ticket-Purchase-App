package com.lhb.movieticketpurchaseapp.model

data class User(
    val id: String? = null,
    val username: String? = null,
    val name: String? = null,
    val email: String?,
    val password: String?,
    val role: Int? = null,
    val avatar: String? = null,
    val phoneNumber: String? = null
)
