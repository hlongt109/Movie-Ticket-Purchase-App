package com.lhb.movieticketpurchaseapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lhb.movieticketpurchaseapp.model.Ticket
import com.lhb.movieticketpurchaseapp.model.TicketFormData
import com.lhb.movieticketpurchaseapp.model.toTicket
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class TicketViewModel(private val repository: UserRepository): ViewModel() {
    private val _ticketsState = MutableLiveData<List<Ticket>>()
    val ticketList: LiveData<List<Ticket>> = _ticketsState

    var ticketFormData = mutableStateOf(TicketFormData())
        private set

    fun setTicketCodeFormData(ticketCode: String){
        ticketFormData.value = ticketFormData.value.copy(ticketCode = ticketCode)
    }
    fun setUserIdFormData(userId: String){
        ticketFormData.value = ticketFormData.value.copy(userId = userId)
    }
    fun setBookingIdFormData(bookingId: String){
        ticketFormData.value = ticketFormData.value.copy(bookingId = bookingId)
    }
    fun setSeatIdFormData(seat_Id: String){
//        ticketFormData.value = ticketFormData.value.copy(seatId = seatId)
        val updatedSeatIds = ticketFormData.value.seatId.toMutableList().apply {
            if (!contains(seat_Id)) {
                add(seat_Id)
            }
        }
        // Update the ticketFormData with the new list of seat IDs
        ticketFormData.value = ticketFormData.value.copy(seatId = updatedSeatIds)
    }
    fun setPriceFormData(price: Double){
        ticketFormData.value = ticketFormData.value.copy(price = price)
    }
    fun setExpirationDateFormData(expirationDate: String){
        ticketFormData.value = ticketFormData.value.copy(expirationDate = expirationDate)
    }
    fun setStatusFormData(status: Boolean){
        ticketFormData.value = ticketFormData.value.copy(status = status)
    }

    fun resetFormData() {
        ticketFormData.value = TicketFormData()
    }

    init {
        loadAllTickets()
    }
    fun loadAllTickets(){
         viewModelScope.launch {
             try{
                 val response = repository.getAllTicket()
                 if(response.isSuccessful){
                     _ticketsState.postValue(response.body()?.map { it.toTicket() })
                 }else{
                     _ticketsState.postValue(emptyList())
                 }
             }catch (e: Exception){
                 Log.e("Tag", "get ticket list :" + e.message)
                 _ticketsState.postValue(emptyList())
             }
         }
    }
    fun filterTicketsBeforeCurrentDate(currentDate: String,userId: String): LiveData<List<Ticket>> {
        val result = MutableLiveData<List<Ticket>>()
        val dateFormat = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault())

        _ticketsState.value?.let { tickets ->
            val filteredTickets = tickets.filter { ticket ->
                try {
                    val expirationDate = dateFormat.parse(ticket.expirationDate)
                    val current = dateFormat.parse(currentDate)
                    expirationDate != null && current != null && !expirationDate.before(current) && ticket.userId == userId
                } catch (e: Exception) {
                    false
                }
            }
            result.postValue(filteredTickets)
        }
        return result
    }

    fun filterTicketsByUserId(userId: String): LiveData<List<Ticket>> {
        val result = MutableLiveData<List<Ticket>>()

        _ticketsState.value?.let { tickets ->
            val filteredTickets = tickets.filter { ticket ->
                try {
                    ticket.userId == userId
                } catch (e: Exception) {
                    false
                }
            }
            result.postValue(filteredTickets)
        }

        return result
    }

    fun createTickets(
        formData: TicketFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.createTicket(formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllTickets()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("Tag", "add ticket: " + e.message)
                onResult(false)
            }
        }
    }
    fun updateTicket(
        id: String,
        formData: TicketFormData,
        onResult: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            try {
                val response = repository.updateTicket(id,formData)
                if(response.isSuccessful && response.body()?.status == 200){
                    loadAllTickets()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e:Exception){
                Log.e("Tag", "update ticket: " + e.message)
                onResult(false)
            }
        }
    }
    fun getTicketById(id: String): LiveData<Ticket?>{
        val result = MutableLiveData<Ticket?>()
        viewModelScope.launch {
            try {
                val response = repository.getTicketById(id)
                if(response.isSuccessful){
                    result.postValue(response.body()?.toTicket())
                }else{
                    result.postValue(null)
                }
            }catch (e:Exception){
                Log.e("Tag", "get ticket by id: " + e.message)
                result.postValue(null)
            }
        }
        return result
    }
}
class TicketViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(TicketViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TicketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}