package com.lhb.movieticketpurchaseapp.network

import com.lhb.movieticketpurchaseapp.model.Response
import com.lhb.movieticketpurchaseapp.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
  @POST("/api/login")
  suspend fun login(@Body user:User): Response<User>

}