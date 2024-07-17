package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.FoodAndDrink
import com.lhb.movieticketpurchaseapp.model.FoodAndDrinkFormData
import com.lhb.movieticketpurchaseapp.model.toFoodDrink
import com.lhb.movieticketpurchaseapp.model.toShowTime
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import kotlinx.coroutines.launch

class FoodDrinkViewModel(private val repository: ManagerRepository): ViewModel(){
    private val _foodDrinkList = MutableLiveData<List<FoodAndDrink>>()
    val foodDrinkList: LiveData<List<FoodAndDrink>> = _foodDrinkList

    constructor(): this(ManagerRepository(RetrofitService())){}

    init {
        getAllFoodDrinks()
    }
    fun getAllFoodDrinks(){
        viewModelScope.launch {
            try {
                val response = repository.getAllFoodDrinks()
                if(response.isSuccessful){
                    _foodDrinkList.postValue(response.body()?.map { it.toFoodDrink() })
                }else{
                    _foodDrinkList.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "get food drink list :" + e.message)
                _foodDrinkList.postValue(emptyList())
            }
        }
    }
    fun addFoodDrink(
        formData: FoodAndDrinkFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.addFoodDrink(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllFoodDrinks()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "add food drink: " + e.message)
                onResult(false)
            }
        }
    }
    fun updateFoodDrinks(
        id: String,
        formData: FoodAndDrinkFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateFoodDrink(id, formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllFoodDrinks()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "update food drink: " + e.message)
                onResult(false)
            }
        }
    }
    fun deleteFoodDrink(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteFoodDrink(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllFoodDrinks()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete food drink: " + e.message)
                onResult(false)
            }
        }
    }
    fun getFoodDrinkById(id: String): LiveData<FoodAndDrink?>{
        val result = MutableLiveData<FoodAndDrink?>()
        viewModelScope.launch {
            try {
                val response = repository.getDetailFoodDrink(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toFoodDrink())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get food drink by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class FoodDrinkModelFactory(private val repository: ManagerRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(FoodDrinkViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FoodDrinkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}