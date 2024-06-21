package com.lhb.movieticketpurchaseapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val manageService: ManageService by lazy {
        retrofit.create(ManageService::class.java)
    }
    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}