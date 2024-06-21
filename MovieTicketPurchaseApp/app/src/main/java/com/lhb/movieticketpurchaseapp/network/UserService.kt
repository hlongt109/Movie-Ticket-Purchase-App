package com.lhb.movieticketpurchaseapp.network

import com.lhb.movieticketpurchaseapp.model.Response
import com.lhb.movieticketpurchaseapp.model.StatusResponse
import com.lhb.movieticketpurchaseapp.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
  @POST("/api/login")
  suspend fun login(@Body user:UserModel): Response<UserModel>

}