package com.lhb.movieticketpurchaseapp.model

data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val email: String?,
    val phoneNumber: String? = null,
    val password: String?,
    val avatar: String? = null,
    val role: Int? = null
)
