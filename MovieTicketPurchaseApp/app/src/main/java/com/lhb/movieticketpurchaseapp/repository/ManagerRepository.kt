package com.lhb.movieticketpurchaseapp.repository

import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class ManagerRepository(private val retrofitService: RetrofitService) {
    suspend fun createMovieGenre(movieType: MovieType) = retrofitService.managerService.createMovieType(
        movieType.name.toRequestBody("text/plain".toMediaTypeOrNull())
    )
    suspend fun updateMovieGenre(movieType: MovieType) = retrofitService.managerService.updateMovieType(
        movieType.id,
        movieType.name.toRequestBody("text/plain".toMediaTypeOrNull())
    )
    suspend fun deleteMovieGenre(movieType: MovieType) = retrofitService.managerService.deleteMovieType(movieType.id)
}