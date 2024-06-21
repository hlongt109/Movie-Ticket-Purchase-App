package com.lhb.movieticketpurchaseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lhb.movieticketpurchaseapp.model.Movie

class MovieViewModel: ViewModel() {
    private val _listMovies = MutableLiveData<List<Movie>>()
    val listMovies: LiveData<List<Movie>> = _listMovies
}