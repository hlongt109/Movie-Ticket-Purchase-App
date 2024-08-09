package com.lhb.movieticketpurchaseapp.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.LoginRequest
import com.lhb.movieticketpurchaseapp.model.User
import com.lhb.movieticketpurchaseapp.model.UserFormData
import com.lhb.movieticketpurchaseapp.model.toUser
import com.lhb.movieticketpurchaseapp.model.toUserLogin
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository, context: Context) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _userList

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private fun saveUserInfo(userId: String?, role: Int) {
        sharedPreferences.edit().apply {
            putString("user_id", userId)
            putInt("user_role", role)
            apply()
        }
    }

    fun login(email: String?, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email ?: "", password)
                val response = repository.login(loginRequest)
                if (response.isSuccessful && response.body()?.status == 200) {
                    val loginResponse = response.body()?.toUserLogin()
                    saveUserInfo(loginResponse?.id, loginResponse?.role ?: -1)
                    onResult(true)
                    Log.d("TAG", "login data id: "+loginResponse?.id)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("TAG", "login: "+e.message)
                onResult(false)
            }
        }
    }

    fun getUserId(): String? = sharedPreferences.getString("user_id", null)
    fun getUserRole(): Int = sharedPreferences.getInt("user_role", -1)

    fun signUp(
        formData: UserFormData,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.signUp(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("TAG", "signUp: "+e.message)
                onResult(false)
            }
        }
    }
    init {
        getAllUser()
    }
    fun getAllUser(){
        viewModelScope.launch {
            try {
                val response = repository.getAllUser()
                if(response.isSuccessful){
                    _userList.postValue(response.body()?.map { it.toUser() })
                }else{
                    _userList.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "get user list:" + e.message)
                _userList.postValue(emptyList())
            }
        }
    }
    fun addUserAcc(
        formData: UserFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.addUser(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllUser()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("TAG", "add acc: "+e.message )
                onResult(false)
            }
        }
    }
    fun updateInfomationAccount(
        id: String,
        formData: UserFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.upadateInfomationAcount(id,formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllUser()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("TAG", "update information: "+e.message )
                onResult(false)
            }
        }
    }
    fun updateUser(
        id: String,
        formData: UserFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateUser(id,formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllUser()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("TAG", "update user: "+e.message )
                onResult(false)
            }
        }
    }
    fun deleteAccount(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteAccount(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllUser()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("TAG", "delete account: "+e.message)
                onResult(false)
            }
        }
    }
    fun getInfomationAccountById(id: String): LiveData<User?>{
        val result = MutableLiveData<User?>()
        viewModelScope.launch {
            try {
                val response = repository.getInfomationAccDetails(id)
                if(response.isSuccessful){
                    val data = response.body()
                    result.postValue(data?.toUser())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception) {
                Log.e("TAG", "get information account: "+e.message )
                result.postValue(null)
            }
        }
        return result
    }
}
class UserViewModelFactory(
    private val repository: UserRepository,
    private val context: Context
    ): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository,context) as T
        }
        throw IllegalArgumentException("Unknown movieViewModel class")
    }
}