package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.BookingFormData
import com.lhb.movieticketpurchaseapp.model.BookingItem
import com.lhb.movieticketpurchaseapp.model.BookingItemFormData
import com.lhb.movieticketpurchaseapp.model.toBookingItem
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch

class BookingItemViewModel(private val repository: UserRepository) : ViewModel() {
    private val _bookingItemState = MutableLiveData<List<BookingItem>>()
    val bookingItemList: LiveData<List<BookingItem>> = _bookingItemState

    // ----------------------------------------------------------------
    var bookingItemFormDataList = mutableStateOf(listOf<BookingItemFormData>())
        private set


    fun getItemCount(): Int {
        return bookingItemFormDataList.value.size
    }

    fun addBookingItemFormData(bookingItemFormData: BookingItemFormData) {
        val newList = bookingItemFormDataList.value.toMutableList()
        val existingIndex = newList.indexOfFirst { it.itemId == bookingItemFormData.itemId }
        if (existingIndex != -1) {
            val existingItem = newList[existingIndex]
            newList[existingIndex] = existingItem.copy(quantity = existingItem.quantity + bookingItemFormData.quantity)
        } else {
            newList.add(bookingItemFormData)
        }
        bookingItemFormDataList.value = newList
    }
    fun removeBookingItemFormData(bookingItemFormData: BookingItemFormData) {
        val newList = bookingItemFormDataList.value.toMutableList()
        newList.remove(bookingItemFormData)
        bookingItemFormDataList.value = newList
    }

    fun updateBookingItemFormData(itemId: String, quantity: Int) {
        val newList = bookingItemFormDataList.value.toMutableList()
        val index = newList.indexOfFirst { it.itemId == itemId }
        if (index != -1) {
            newList[index] = newList[index].copy(quantity = quantity)
            bookingItemFormDataList.value = newList
        }
    }
    fun calculateTotalAmount(): Double {
        return bookingItemFormDataList.value.sumOf { item ->
            item.price * item.quantity
        }
    }

    fun updateBookingIdForAllItems(newBookingId: String) {
        // Update the bookingId for each item in the list
        val updatedList = bookingItemFormDataList.value.map { item ->
            item.copy(bookingId = newBookingId)
        }
        // Update the state with the new list
        bookingItemFormDataList.value = updatedList
    }

    fun addAllBookingItems(
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                var allSuccessful = true
                for (bookingItem in bookingItemFormDataList.value) {
                    val response = repository.createBookingItem(bookingItem)
                    if (!response.isSuccessful || response.body()?.status != 200) {
                        allSuccessful = false
                    }
                }
                loadAllBookingItem()
                onResult(allSuccessful)
            } catch (e: Exception) {
                Log.e("Tag", "add all booking items: " + e.message)
                onResult(false)
            }
        }
    }
    fun resetFormData() {
        bookingItemFormDataList.value = emptyList()
    }

    // ----------------------------------------------------------------

    init {
        loadAllBookingItem()
    }

    fun loadAllBookingItem() {
        viewModelScope.launch {
            try {
                val response = repository.getAllBookingItem()
                if (response.isSuccessful) {
                    _bookingItemState.postValue(response.body()?.map { it.toBookingItem() })
                } else {
                    _bookingItemState.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("Tag", "get bookingItem list :" + e.message)
                _bookingItemState.postValue(emptyList())
            }
        }
    }

    fun getListBookingItemByBookingId(bookingId: String): LiveData<List<BookingItem>?>{
        val result = MutableLiveData<List<BookingItem>?>()
        viewModelScope.launch {
            try {
                val filterList = _bookingItemState.value?.filter { it.bookingId == bookingId }
                result.postValue(filterList)
            }catch (e:Exception){
                Log.e("Tag", "get booking item by bookingId: " + e.message)
                result.postValue(emptyList())
            }
        }
        return result
    }

    fun addBookingItem(
        bookingItem: BookingItemFormData,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.createBookingItem(bookingItem)
                if (response.isSuccessful && response.body()?.status == 200) {
                    loadAllBookingItem()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "add booking item: " + e.message)
                onResult(false)
            }
        }
    }

    fun updateBookingItem(
        id: String,
        formData: BookingItemFormData,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.updateBookingItem(id, formData)
                if (response.isSuccessful && response.body()?.status == 200) {
                    loadAllBookingItem()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Tag", "update booking item: " + e.message)
                onResult(false)
            }
        }
    }

    fun deleteBookingItem(
        id: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.deleteBookingItem(id)
                if (response.isSuccessful && response.body()?.status == 200) {
                    loadAllBookingItem()
                    onResult(true)
                } else {
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "delete booking item: " + e.message)
                onResult(false)
            }
        }
    }
    fun getBookingItemById(id: String): LiveData<BookingItem?>{
        val result = MutableLiveData<BookingItem?>()
        viewModelScope.launch {
            try {
                val response = repository.getBookingItemById(id)
                if (response.isSuccessful){
                    result.postValue(response.body()?.toBookingItem())
                }else{
                    result.postValue(null)
                }
            }catch (e: Exception){
                Log.e("Tag", "get booking item by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class BookingItemViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(BookingItemViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return BookingItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}