package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.TimeFrame
import com.lhb.movieticketpurchaseapp.model.TimeFrameFormData
import com.lhb.movieticketpurchaseapp.model.toTimeFrame
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import kotlinx.coroutines.launch

class TimeFrameViewModel(private val repository: ManagerRepository): ViewModel(){
    private val _timeFrameList = MutableLiveData<List<TimeFrame>>()
    val timeFrameList: LiveData<List<TimeFrame>> = _timeFrameList

    constructor(): this(ManagerRepository(RetrofitService())){}

    init {
        getAllTimeFrames()
    }
    fun getAllTimeFrames(){
        viewModelScope.launch {
            try {
                val response = repository.getAllTimeFrame()
                if(response.isSuccessful){
                    _timeFrameList.postValue(response.body()?.map { it.toTimeFrame() })
                }else{
                    _timeFrameList.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "get time frame list :" + e.message)
                _timeFrameList.postValue(emptyList())
            }
        }
    }
    fun getAllTimeFrameIds(): List<String>{
        return _timeFrameList.value?.map { it.id } ?: emptyList()
    }
    fun addTimeFrame(
        timeFrame: TimeFrameFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.addTimeFrame(timeFrame)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllTimeFrames()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "add time frame: " + e.message)
                onResult(false)
            }
        }
    }
    fun updateTimeFrame(
        id: String,
        formData: TimeFrameFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateTimeFrame(id, formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllTimeFrames()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "update time frame: " + e.message)
                onResult(false)
            }
        }
    }
    fun deleteTimeFrame(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteTimeFrame(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllTimeFrames()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete time frame: " + e.message)
                onResult(false)
            }
        }
    }
    fun getTimeFrameById(id: String): LiveData<TimeFrame?>{
        val result = MutableLiveData<TimeFrame?>()
        viewModelScope.launch {
            try {
                val response = repository.getDetailsTimeFrame(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toTimeFrame())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get time frame by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class TimeFrameModelFactory(private val repository: ManagerRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(TimeFrameViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TimeFrameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}