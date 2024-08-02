package com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens

import android.text.Layout.Alignment
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.model.Seat
import com.lhb.movieticketpurchaseapp.model.SeatStatus
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.ItemSeat
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@Composable
fun SelectSeatScreen(
    bookingViewModel: BookingViewModel,
    showTimeViewModel: ShowTimeViewModel,
    userViewModel: UserViewModel,
    ticketViewModel: TicketViewModel,
    seats: List<Seat>, totalSeatsPerRow: Int,
) {

    val currentDate = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault()).format(Date())
    val randomTicketCode = generateTicketCode()

    val showTimeState =  showTimeViewModel.getShowTimeById(bookingViewModel.bookingFormData.value.showTimeId).observeAsState(initial = null).value

    var selectedSeats by remember { mutableStateOf(listOf<Seat>()) }

    val handlePrice = showTimeState?.price?.times(selectedSeats.size) ?: 0.0

    LaunchedEffect(key1 = selectedSeats) {

    }

    val textModifier = Modifier.padding(end = 16.dp, start = 4.dp)
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Text(
            "Screen",
            modifier = Modifier.padding(16.dp),
            fontStyle = FontStyle.Italic,
            fontFamily = Inter,
            fontSize = 28.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns =
            GridCells.Fixed(totalSeatsPerRow)
        ) {
            items(seats.size) { index ->
                ItemSeat(seat = seats[index],
                    selectSeatName = {
                        selectedSeats = if (seats[index] in selectedSeats) {
                            selectedSeats - seats[index]
                        } else {
                            selectedSeats + seats[index]
                        }
                        ticketViewModel.setTicketCodeFormData(randomTicketCode)
                        ticketViewModel.setSeatIdFormData(it)
                        ticketViewModel.setUserIdFormData(userViewModel.getUserId() ?: "")
                        ticketViewModel.setExpirationDateFormData(currentDate)
                        ticketViewModel.setStatusFormData(false)
                        Log.d("TAG", "Ticket form data: ${ticketViewModel.ticketFormData}")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            val exampleEmptySeat = remember {
                Seat(
                    "demo1", 'X', 1,
                    SeatStatus.EMPTY
                )
            }
            val exampleSelectedSeat = remember {
                Seat(
                    "demo2", 'Y', 1,
                    SeatStatus.SELECTED
                )
            }
            val exampleBookedSeat = remember {
                Seat(
                    "demo3", 'Z', 1,
                    SeatStatus.BOOKED
                )
            }
            ItemSeat(exampleEmptySeat, false, selectSeatName = {})
            Text(
                text = "Available",
                fontFamily = Inter,
                fontSize = 15.sp,
                color = Color.White,
                modifier = textModifier
            )
            ItemSeat(exampleSelectedSeat, false, selectSeatName = {})
            Text(
                text = "Selected",
                fontFamily = Inter,
                fontSize = 15.sp,
                color = Color.White,
                modifier = textModifier
            )
            ItemSeat(exampleBookedSeat, false, selectSeatName = {})
            Text(
                text = "Booked",
                fontFamily = Inter,
                fontSize = 15.sp,
                color = Color.White,
                modifier = textModifier
            )
        }
    }
}

fun createTheaterSeating(
    totalRows: Int,
    totalSeatsPerRow: Int,
    aislePositionInRow: Int,
    aislePositionInColumn: Int
): List<Seat> {
    val seats = mutableListOf<Seat>()
    for (rowIndex in 0 until totalRows) {
        for (seatIndex in 1..totalSeatsPerRow) {
            val adjustedRowIndex = if (rowIndex >=
                aislePositionInRow
            ) rowIndex - 1 else rowIndex
            val adjustedSeatIndex = if (seatIndex >= aislePositionInColumn)
                seatIndex - 1 else seatIndex
            val isAisleRow = rowIndex == aislePositionInRow
            val isAisleColumn = seatIndex ==
                    aislePositionInColumn
            val status = when {
                isAisleRow || isAisleColumn -> SeatStatus.AISLE
                else -> if (Random.nextInt(0, 99) % 2 == 0)
                    SeatStatus.BOOKED else SeatStatus.EMPTY
            }
            seats.add(
                Seat(
                    id = rowIndex.toString(),
                    row = 'A' + adjustedRowIndex,
                    number = adjustedSeatIndex,
                    status = status,
                )
            )
        }
    }
    return seats
}
fun generateTicketCode(): String {
    val randomNumber = Random.nextInt(100000, 999999) // Tạo một số ngẫu nhiên 6 chữ số
    return "T$randomNumber"
}