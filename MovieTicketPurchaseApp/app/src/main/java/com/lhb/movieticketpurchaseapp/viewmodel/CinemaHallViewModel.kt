package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.CinemaHall
import com.lhb.movieticketpurchaseapp.model.CinemaHallFormData
import com.lhb.movieticketpurchaseapp.model.toCinemaHall
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import kotlinx.coroutines.launch

class CinemaHallViewModel(private val repository: ManagerRepository): ViewModel(){
    private val _listCinemaHall = MutableLiveData<List<CinemaHall>>()
    val listCinemaHall: LiveData<List<CinemaHall>> get() = _listCinemaHall

    constructor(): this(ManagerRepository(RetrofitService())){}

    init {
        loadCinemaHallList()
    }
    fun loadCinemaHallList(){
        viewModelScope.launch {
            try {
                val response = repository.getAllCinemaHall()
                if(response.isSuccessful){
                    _listCinemaHall.postValue(response.body()?.map { it.toCinemaHall() })
                }else{
                    _listCinemaHall.postValue(emptyList())
                }
            }catch (e:Exception){
                Log.e("Tag", "get cinema hall:" + e.message)
                _listCinemaHall.postValue(emptyList())
            }
        }
    }
    fun getCinemaHallCountByTheaterId(theaterId: String): Int{
        return _listCinemaHall.value?.count { it.theaterId == theaterId } ?: 0
    }
    fun getHallByTheaterId(id: String): LiveData<List<CinemaHall>?>{
        val result = MutableLiveData<List<CinemaHall>?>()
        viewModelScope.launch {
            try {
                val filteredList = _listCinemaHall.value?.filter { it.theaterId == id }
                result.postValue(filteredList)
            }catch (e: Exception){
                Log.e("Tag", "get cinema halls by theaterId: " + e.message)
                result.postValue(emptyList())
            }
        }
        return result
    }
    fun addCinemaHall(
        formData: CinemaHallFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.addCinemaHall(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadCinemaHallList()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e:Exception){
                Log.e("Tag", "add cinema hall error: " + e.message)
                onResult(false)
            }
        }
    }
    fun updateCinemaHall(
        id: String,
        formData: CinemaHallFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateCinemaHall(id,formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadCinemaHallList()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "update cinema hall error: " + e.message)
                onResult(false)
            }
        }
    }
    fun deleteCinemaHall(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteCinemaHall(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadCinemaHallList()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete cinema hall error: " + e.message)
                onResult(false)
            }
        }
    }
    fun getCinemaHallById(id: String): LiveData<CinemaHall?>{
        val result = MutableLiveData<CinemaHall?>()
        viewModelScope.launch {
            try {
                val response = repository.getCinemaHallById(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toCinemaHall())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get cinema hall by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class CinemaHallViewModelFactory(private val repository: ManagerRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(CinemaHallViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CinemaHallViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}