package com.lhb.movieticketpurchaseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lhb.movieticketpurchaseapp.model.Movie
import com.lhb.movieticketpurchaseapp.model.MovieType

class MovieTypeViewModel: ViewModel() {
    private val _listMovieTypes = MutableLiveData<List<MovieType>>()
    val listMovieTypes: LiveData<List<MovieType>> = _listMovieTypes
}