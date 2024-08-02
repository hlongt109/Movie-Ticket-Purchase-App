package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.User
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val loginError = mutableStateOf("")
    val role = mutableStateOf<Int?>(null)

    fun validateLogin() : Boolean{
        var isValid = true
        if(!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
            emailError.value = "Invalid email format"
            isValid = false
        }else{
            emailError.value = ""
        }

        if(password.value.length < 6){
            passwordError.value = "Password must be at least 6 characters"
            isValid = false
        }else{
            passwordError.value = ""
        }
        return isValid
    }

}

class LoginViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}