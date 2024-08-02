package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.map
import com.lhb.movieticketpurchaseapp.model.Ticket
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel

@Preview(showBackground = true )
@Composable
fun ItemTicket(
    ticket: Ticket,
    ticketViewModel: TicketViewModel,
    bookingViewModel: BookingViewModel,
    showTimeViewModel: ShowTimeViewModel,
    timeFrameViewModel: TimeFrameViewModel,
    bookingItemViewModel: BookingItemViewModel,
    foodDrinkViewModel: FoodDrinkViewModel,
    theaterViewModel: TheaterViewModel
){
    val ticketState = ticketViewModel.getTicketById(ticket.id).observeAsState(initial = null).value
    val bookingState = bookingViewModel.getBookingById(ticketState?.bookingId ?:"").observeAsState(initial = null).value
    val showTimeState = showTimeViewModel.getShowTimeById(bookingState?.showTimeId ?:"").observeAsState(initial = null).value
    val timeFrameState = timeFrameViewModel.getTimeFrameById(bookingState?.timeFrameId ?:"").observeAsState(initial = null).value
    val itemsBookingState = bookingItemViewModel.getListBookingItemByBookingId(bookingState?.id ?:"").observeAsState(initial = emptyList()).value
    val itemIds = itemsBookingState?.map { it.itemId }
    val foodDrinks = foodDrinkViewModel.foodDrinkList.observeAsState(initial = emptyList()).value
    val theaterState = theaterViewModel.getTheaterById(showTimeState?.theaterId ?:"").observeAsState(initial = null).value
    //
    val filteredFoodDrinks = foodDrinks.filter { foodDrink -> itemIds?.contains(foodDrink.id) == true }
    // Generate the desired string
    val foodDrinkString = filteredFoodDrinks.joinToString(separator = "\n") { foodDrink ->
        val quantity = itemsBookingState?.find { it.itemId == foodDrink.id }?.quantity ?: 0
        "- ${foodDrink.name} ($quantity)"
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color("#000000".toColorInt()).copy(alpha = 0.8f),
            contentColor = Color("#ffffff".toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 4.dp,
            focusedElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB),
                spotColor = Color(0xff6C47DB)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row {
                    Text(
                        text = "Ticket code :",
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = ticketState?.ticketCode ?: "None",
                        color = Color(0xff33B528),
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Movie :",
                        color = Color.LightGray,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = showTimeState?.nameMovie ?: "None",
                        color = Color.LightGray,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                // section
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Exp :",
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "(${timeFrameState?.startTime} - ${timeFrameState?.endTime}) " + ticketState?.expirationDate,
                        color = Color(0xff33B528),
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                // Seat Number
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Seat number :",
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = ticketState?.seatId?.joinToString(separator = ", ") ?: "None",
                        color = Color(0xff33B528),
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                // Buffet Products
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        text = "Buffet products:",
                        color = Color.Gray,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = foodDrinkString,
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                // Movie Theater
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        text = "Movie theater :",
                        color = Color.Gray,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = theaterState?.name?: "None",
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = theaterState?.location ?: "None",
                        color = Color.White,
                        fontFamily = Inter,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = (ticketState?.price?.toString() + " vnd"),
                        color = Color(0xff33B528),
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}