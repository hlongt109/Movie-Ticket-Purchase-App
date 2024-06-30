package com.lhb.movieticketpurchaseapp.repository

import com.lhb.movieticketpurchaseapp.model.User
import com.lhb.movieticketpurchaseapp.network.RetrofitService

class UserRepository(private val retrofitService: RetrofitService) {
    suspend fun login(user: User) = retrofitService.userService.login(user)
}