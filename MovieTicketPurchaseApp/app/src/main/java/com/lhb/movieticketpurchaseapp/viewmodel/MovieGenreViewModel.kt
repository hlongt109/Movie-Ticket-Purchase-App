package com.lhb.movieticketpurchaseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lhb.movieticketpurchaseapp.model.MovieType

class MovieGenreViewModel: ViewModel() {
    private val _listMovieTypes = MutableLiveData<List<MovieType>>()
    val listMovieTypes: LiveData<List<MovieType>> = _listMovieTypes

    private val _listMovieTypesCategory = MutableLiveData<List<String>>()
    val listMovieCategory: LiveData<List<String>> = _listMovieTypesCategory
}