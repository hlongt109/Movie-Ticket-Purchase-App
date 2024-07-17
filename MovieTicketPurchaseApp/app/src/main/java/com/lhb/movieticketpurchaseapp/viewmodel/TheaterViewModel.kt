package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.Theater
import com.lhb.movieticketpurchaseapp.model.TheaterFormData
import com.lhb.movieticketpurchaseapp.model.toTheater
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import kotlinx.coroutines.launch

class  TheaterViewModel(private val repository : ManagerRepository) : ViewModel(){
    private val _TheaterList = MutableLiveData<List<Theater>>()
    val theaterList: LiveData<List<Theater>> = _TheaterList

    constructor() : this(ManagerRepository(RetrofitService())){}

    init {
        getAllTheater()
    }
    fun getAllTheater(){
        viewModelScope.launch {
            try {
                val response = repository.getAllTheater()
                if(response.isSuccessful){
                    _TheaterList.postValue(response.body()?.map { it.toTheater() })
                }else{
                    _TheaterList.postValue(emptyList())
                }
            }catch (e: Exception) {
                Log.e("Tag", "get theater :" + e.message)
                _TheaterList.postValue(emptyList())
            }
        }
    }
    fun addNewTheater(
        formData: TheaterFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.createTheater(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllTheater()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception) {
                Log.e("Tag", "add theater: " + e.message)
                onResult(false)
            }
        }
    }
    fun updateTheater(
        id: String,
        formData: TheaterFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateTheater(id,formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllTheater()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "update theater: " + e.message)
                onResult(false)
            }
        }
    }
    fun deleteTheater(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteTheater(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllTheater()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete theater: " + e.message)
                onResult(false)
            }
        }
    }
    fun getTheaterById(id: String): LiveData<Theater?>{
        val result = MutableLiveData<Theater?>()
        viewModelScope.launch {
            try {
                val response = repository.getTheaterById(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toTheater())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get theater by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class TheaterViewModelFactory(private val repository: ManagerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("TheaterViewModelFactory", "Creating TheaterViewModel instance")
        if (modelClass.isAssignableFrom(TheaterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TheaterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}