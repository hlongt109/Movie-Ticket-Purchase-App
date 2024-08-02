package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.Payment
import com.lhb.movieticketpurchaseapp.model.PaymentFormData
import com.lhb.movieticketpurchaseapp.model.ShowTime
import com.lhb.movieticketpurchaseapp.model.toPayment
import com.lhb.movieticketpurchaseapp.model.toShowTime
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch

class PaymentViewModel(private val repository: UserRepository): ViewModel() {
    private val _paymentsState = MutableLiveData<List<Payment>>()
    val paymentList: LiveData<List<Payment>> = _paymentsState

    var paymentFormData = mutableStateOf(PaymentFormData())
        private set

    fun setMethodFormData(method: String){
        paymentFormData.value = paymentFormData.value.copy(method = method)
    }
    fun setStatusFormData(status: Boolean){
        paymentFormData.value = paymentFormData.value.copy(status = status)
    }
    fun setTransactionDateFormData(transactionDate: String){
        paymentFormData.value = paymentFormData.value.copy(transactionDate = transactionDate)
    }
    fun resetFormData() {
        paymentFormData.value = PaymentFormData()
    }

    init {
        getAllPayment()
    }

    fun getAllPayment(){
        viewModelScope.launch {
            try {
                val response = repository.getAllPayment()
                if(response.isSuccessful){
                    _paymentsState.postValue(response.body()?.map { it.toPayment() })
                }else{
                    _paymentsState.postValue(emptyList())
                }
            }catch (e: Exception){
                Log.e("Tag", "get payment list :" + e.message)
                _paymentsState.postValue(emptyList())
            }
        }
    }
    fun createPayment(
        paymentFormData: PaymentFormData,
        onResult: (Pair<Boolean, String?>) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.createPayment(paymentFormData)
                if(response.isSuccessful && response.body()?.status == 200){
                    getAllPayment()
                    val id = response.body()?.idReturn
                    onResult(Pair(true, id))
                }else{
                    onResult(Pair(true, ""))
                }
            }catch (e: Exception){
                Log.e("Tag", "create payment :" + e.message)
                onResult(Pair(true, ""))
            }
        }
    }
    fun getPaymentById (id: String): LiveData<Payment?>{
        val result = MutableLiveData<Payment?>()
        viewModelScope.launch {
            try {
                val response = repository.getPaymentById(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toPayment())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get payment by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class PaymentViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(PaymentViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PaymentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}