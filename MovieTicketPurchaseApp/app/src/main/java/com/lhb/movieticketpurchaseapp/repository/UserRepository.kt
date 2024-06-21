package com.lhb.movieticketpurchaseapp.repository

import com.lhb.movieticketpurchaseapp.model.UserModel
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.network.UserService

class UserRepository(private val retrofitService: RetrofitService) {
    suspend fun login(user: UserModel) = retrofitService.userService.login(user)
}