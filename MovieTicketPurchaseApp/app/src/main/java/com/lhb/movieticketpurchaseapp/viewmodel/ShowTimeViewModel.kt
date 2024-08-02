package com.lhb.movieticketpurchaseapp.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.ShowTime
import com.lhb.movieticketpurchaseapp.model.ShowTimeFormData
import com.lhb.movieticketpurchaseapp.model.toShowTime
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ShowTimeViewModel(private val repository: ManagerRepository): ViewModel(){
    private val _showTimeList = MutableLiveData<List<ShowTime>>()
    val showTimeList: LiveData<List<ShowTime>> = _showTimeList

    constructor(): this(ManagerRepository(RetrofitService())){}

    init {
        getAllShowTime()
    }
    fun getAllShowTime(){
        viewModelScope.launch {
            try {
                val response = repository.getAllShowTime()
                if(response.isSuccessful){
                    _showTimeList.postValue(response.body()?.map { it.toShowTime() })
                }else{
                    _showTimeList.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "get show time list :" + e.message)
                _showTimeList.postValue(emptyList())
            }
        }
    }
    fun getShowTimeCountByTheaterId(theaterId: String): Int {
        return _showTimeList.value?.count { it.theaterId == theaterId } ?: 0
    }
    fun getShowTimeListByTheaterId(id: String) : LiveData<List<ShowTime>?>{
        val result = MutableLiveData<List<ShowTime>?>()
        viewModelScope.launch {
            try {
                val filterList = _showTimeList.value?.filter { it.theaterId == id }
                result.postValue(filterList)
            }catch (e: Exception){
                Log.e("Tag", "get show time by theaterId: " + e.message)
                result.postValue(emptyList())
            }
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getShowTimeByMovieIdAndDate(movieId: String, showTimeDate: String): LiveData<List<ShowTime>> {
        val result = MutableLiveData<List<ShowTime>>()
        viewModelScope.launch {
            try {
                val formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy", Locale.getDefault())
                val parsedShowTimeDate = LocalDate.parse(showTimeDate, formatter)

                val filterList = _showTimeList.value?.filter {
                    it.movieId == movieId && LocalDate.parse(it.showTimeDate, formatter) >= parsedShowTimeDate
                }
                result.postValue(filterList ?: emptyList())
            } catch (e: Exception) {
                Log.e("Tag", "get show time by movieId and date: " + e.message)
                result.postValue(emptyList())
            }
        }
        return result
    }
//    fun getShowTimeByMovieIdAndDate(movieId: String, showTimeDate: String): LiveData<List<ShowTime>>{
//        val result = MutableLiveData<List<ShowTime>>()
//        viewModelScope.launch {
//            try {
////                val currentDate = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault()).format(Date())
//                val filterList = _showTimeList.value?.filter { it.movieId == movieId && it.showTimeDate >= showTimeDate }
//                result.postValue(filterList ?: emptyList())
//            }catch (e: Exception){
//                Log.e("Tag", "get show time by movieId and date: " + e.message)
//                result.postValue(emptyList())
//            }
//        }
//        return result
//    }
    fun addShowTime(
        formData: ShowTimeFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.addShowTime(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllShowTime()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "add show time: " + e.message)
                onResult(false)
            }
        }
    }

    fun getHallListHaveShowTime(theaterId: String, showTimeDate: String): LiveData<List<String>>{
        val result = MutableLiveData<List<String>>()
        viewModelScope.launch {
            try {
                val filteredHalls = _showTimeList.value
                    ?.filter { it.theaterId == theaterId && it.showTimeDate == showTimeDate}
                    ?.map { it.cinemaHallId }
                    ?.distinct() // Lọc ra các phòng chiếu không trùng lặp
                result.postValue(filteredHalls ?: emptyList())
            }catch (e: Exception) {
                Log.e("Tag", "get halls by theaterId and showTimeDate: " + e.message)
                result.postValue(emptyList())
            }
        }
        return result
    }

    fun updateShowTime(
        id: String,
        formData: ShowTimeFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateShowTime(id, formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllShowTime()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "update show time: " + e.message)
                onResult(false)
            }
        }
    }
    fun deleteShowTime(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteShowTime(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllShowTime()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete show time: " + e.message)
                onResult(false)
            }
        }
    }
    fun getShowTimeById(id: String): LiveData<ShowTime?>{
        val result = MutableLiveData<ShowTime?>()
        viewModelScope.launch {
            try {
                val response = repository.getShowTimeById(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toShowTime())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get show time by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class ShowTimeViewModelFactory(private val repository: ManagerRepository):ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(ShowTimeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ShowTimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}