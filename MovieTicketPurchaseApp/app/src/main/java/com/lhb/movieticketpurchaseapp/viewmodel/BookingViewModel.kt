package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.Booking
import com.lhb.movieticketpurchaseapp.model.BookingFormData
import com.lhb.movieticketpurchaseapp.model.BookingItemFormData
import com.lhb.movieticketpurchaseapp.model.toBooking
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch

class BookingViewModel(private val repository: UserRepository): ViewModel(){
    private val _bookingState = MutableLiveData<List<Booking>>()
    val bookingList: LiveData<List<Booking>> = _bookingState
    //
    var bookingFormData = mutableStateOf(BookingFormData())
        private set

    fun setUserIdFormData(userId: String){
        bookingFormData.value = bookingFormData.value.copy(userId = userId)
    }
    fun setShowTimeIdFormData(showTimeId: String){
        bookingFormData.value = bookingFormData.value.copy(showTimeId = showTimeId)
    }
    fun setTimeFrameIdFormData(timeFrameId: String){
        bookingFormData.value = bookingFormData.value.copy(timeFrameId = timeFrameId)
    }
    fun setPaymentIdFormData(paymentId: String){
        bookingFormData.value = bookingFormData.value.copy(paymentId = paymentId)
    }
    fun setBookingDateFormData(bookingDate: String){
        bookingFormData.value = bookingFormData.value.copy(bookingDate = bookingDate)
    }
    fun setTotalAmountFormData(totalAmount: Double){
        bookingFormData.value = bookingFormData.value.copy(totalAmount = totalAmount)
    }

    fun resetFormData() {
        bookingFormData.value = BookingFormData()
    }
    init {
        loadAllBooking()
    }
     fun loadAllBooking(){
         viewModelScope.launch {
             try {
                 val response = repository.getAllBooking()
                 if(response.isSuccessful){
                     _bookingState.postValue(response.body()?.map { it.toBooking() })
                 }else{
                     _bookingState.postValue(emptyList())
                 }
             }catch (e: Exception){
                 Log.e("TAG", "add booking: "+ e.message, )
                 _bookingState.postValue(emptyList())
             }
         }
     }

    fun addBooking(
        formData: BookingFormData,
        onResult: (Pair<Boolean, String?>) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.createBooking(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllBooking()
                    val id = response.body()?.idReturn
                    onResult(Pair(true, id))
                }else{
                    onResult(Pair(true, ""))
                }
            }catch (e: Exception){
                Log.e("Tag", "add booking: " + e.message)
                onResult(Pair(true, ""))
            }
        }
    }
    fun deleteBooking(
        id: String,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.deleteBooking(id)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllBooking()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete booking: " + e.message)
                onResult(false)
            }
        }
    }
    fun getBookingById(id: String): LiveData<Booking?>{
        val result = MutableLiveData<Booking?>()
        viewModelScope.launch {
            try {
                val response = repository.getBookingById(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toBooking())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get booking by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class BookingViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(BookingViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return BookingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
