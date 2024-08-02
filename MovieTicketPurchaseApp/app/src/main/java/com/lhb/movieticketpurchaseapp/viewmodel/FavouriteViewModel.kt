package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.Favourite
import com.lhb.movieticketpurchaseapp.model.FavouriteFormData
import com.lhb.movieticketpurchaseapp.model.toFavourite
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: UserRepository): ViewModel() {
    private val _favouriteState = MutableLiveData<List<Favourite>>()
    val favoriteList: LiveData<List<Favourite>> = _favouriteState

    init {
        getFavoriteList()
    }
    fun getFavoriteList(){
        viewModelScope.launch {
            try {
                val response = repository.getFavouriteList()
                if(response.isSuccessful){
                    _favouriteState.postValue(response.body()?.map { it.toFavourite() })
                }else{
                    _favouriteState.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("TAG", "getFavoriteList: "+e.message, )
                _favouriteState.postValue(emptyList())
            }
        }
    }
    fun addFavorite(
        formData: FavouriteFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.addFavourite(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getFavoriteList()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch(e: Exception){
                Log.e("TAG", "addFavorite: "+e.message )
                onResult(false)
            }
        }
    }
    fun removeFavorite(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.removeFavourite(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    getFavoriteList()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch(e: Exception){
                Log.e("TAG", "removeFavorite: "+e.message )
                onResult(false)
            }
        }
    }

    fun getFavourite(movieId: String, userId: String): LiveData<Favourite?> {
        val result = MutableLiveData<Favourite?>()
        viewModelScope.launch {
            try {
                val favorites = _favouriteState.value
                val favourite = favorites?.find { it.movieId == movieId && it.userId == userId }
                result.postValue(favourite)
            } catch (e: Exception) {
                Log.e("TAG", "getFavourite: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class FavouriteViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavouriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}