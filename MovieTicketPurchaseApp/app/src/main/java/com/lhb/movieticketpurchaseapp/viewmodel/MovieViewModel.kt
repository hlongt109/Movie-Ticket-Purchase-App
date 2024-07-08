package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.Movie
import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.toMovie
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: ManagerRepository) : ViewModel() {
    private val _listMovies = MutableLiveData<List<Movie>>()
    val listMovies: LiveData<List<Movie>> = _listMovies

    constructor() : this(ManagerRepository(RetrofitService()))

    init {
        loadAllMovies()
    }

    fun loadAllMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getAllMovies()
                if(response.isSuccessful){
                    _listMovies.postValue(response.body()?.map { it.toMovie() })
                }else{
                    _listMovies.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("TAG", "loadAllMovies: "+ e.message, )
                _listMovies.postValue(emptyList())
            }
        }
    }

    fun addNewMovie(
        formData: MovieFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.createMovie(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllMovies()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception) {
                Log.e("Tag", "Error, add movie: " + e.message)
                onResult(false)
            }
        }
    }

    fun updateMovie(
        id: String,
        formData: MovieFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateMovie(id,formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllMovies()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception) {
                Log.e("TAG", "updateMovie: "+e.message )
                onResult(false)
            }
        }
    }

    fun deleteMovie(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteMovie(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllMovies()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception) {
                Log.e("TAG", "deleteMovie: "+e.message)
                onResult(false)
            }
        }
    }
    fun getMovieById(id: String) : LiveData<Movie?>{
        val result = MutableLiveData<Movie?>()
        viewModelScope.launch {
            try {
                val response = repository.getMovieById(id)
                if(response.isSuccessful){
                    val responseBody = response.body()
                    Log.d("TAG", "Response Body: $responseBody")
                    result.postValue(responseBody?.toMovie())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("TAG", "getMovieById: "+e.message )
                result.postValue(null)
            }
        }
        return result
    }
}
class MovieViewModelFactory(private val repository: ManagerRepository) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown movieViewModel class")
    }
}