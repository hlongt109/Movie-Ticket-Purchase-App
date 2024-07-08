package com.lhb.movieticketpurchaseapp.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.model.MovieTypeFormData
import com.lhb.movieticketpurchaseapp.model.toMovieType
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import kotlin.math.log

class MovieGenreViewModel(private val repository: ManagerRepository) : ViewModel() {
    private val _listMovieTypes = MutableLiveData<List<MovieType>>()
    val listMovieTypes: LiveData<List<MovieType>> = _listMovieTypes

    // category
    private val _listMovieTypesCategory = MutableLiveData<List<String>>()
    val listMovieCategory: LiveData<List<String>> = _listMovieTypesCategory

    constructor() : this(ManagerRepository(RetrofitService())) {}

    init {
        loadAllMovieGenre()
    }

    fun loadAllMovieGenre() {
        viewModelScope.launch {
            try {
                val response = repository.getAllMovieGenre()
                if (response.isSuccessful) {
                    _listMovieTypes.postValue(response.body()?.map { it.toMovieType() })
                    _listMovieTypesCategory.postValue(response.body()?.map { it.name })
                } else {
                    _listMovieTypes.postValue(emptyList())
                    _listMovieTypesCategory.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("Tag", "get movie genre:" + e.message)
                _listMovieTypes.postValue(emptyList())
                _listMovieTypesCategory.postValue(emptyList())
            }
        }
    }

    fun addNewMovieGenre(
        formData: MovieTypeFormData,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("Tag", "addNewMovieGenre: " + formData.name)
                val response = repository.createMovieGenre(formData)
                if (response.isSuccessful && response.body()?.status == 200) {
                    loadAllMovieGenre()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "add movie genre: " + e.message)
                onResult(false)
            }
        }
    }

    fun updateMovieGenre(
        id: String,
        formData: MovieTypeFormData,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.updateMovieGenre(id,formData)
                if (response.isSuccessful && response.body()?.status == 200) {
                    loadAllMovieGenre()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "update movie genre: " + e.message)
                onResult(false)
            }
        }
    }

    fun deleteMovieGenre(
        id: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.deleteMovieGenre(id)
                if (response.isSuccessful && response.body()?.status == 200) {
                    loadAllMovieGenre()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "delete movie genre: " + e.message)
                onResult(false)
            }
        }
    }

    fun getMovieGenreById(id: String): LiveData<MovieType?> {
        val result = MutableLiveData<MovieType?>()
        viewModelScope.launch {
            try {
                val reponse = repository.getMovieGenreById(id)
                if (reponse.isSuccessful) {
                    result.postValue(reponse.body()?.toMovieType())
                } else {
                    result.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("Tag", "get movie genre by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class MovieGenreViewModelFactory(private val repository: ManagerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("MovieGenreViewModelFactory", "Creating MovieGenreViewModel instance")
        if (modelClass.isAssignableFrom(MovieGenreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieGenreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

