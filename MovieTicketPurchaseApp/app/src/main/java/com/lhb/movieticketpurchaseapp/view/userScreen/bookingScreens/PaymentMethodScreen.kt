package com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.TopBarBooking
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.PaymentViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log

@Composable
fun PaymentMethodScreen(
    paymentViewModel: PaymentViewModel,
    bookingItemViewModel: BookingItemViewModel,
    bookingViewModel: BookingViewModel,
    ticketViewModel: TicketViewModel,
    showTimeViewModel: ShowTimeViewModel,
    timeFrameViewModel: TimeFrameViewModel
) {
    val imageMethodList = listOf(
        Pair(R.drawable.apay, "Apple Pay"),
        Pair(R.drawable.mpay, "Master Cart"),
        Pair(R.drawable.ppay, "PayPal"),
        Pair(R.drawable.gpay, "Google Pay")
    )

    var selectedImage by remember { mutableStateOf<Int?>(null) }
    var selectedMethodName by remember { mutableStateOf<String?>(null) }
    val currentDate = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault()).format(Date())

    val timeFrameId = bookingViewModel.bookingFormData.value.timeFrameId
    val timeFrame by timeFrameViewModel.getTimeFrameById(timeFrameId).observeAsState(initial = null)
    val section = timeFrame?.startTime + " - " + timeFrame?.endTime

    val seatNameList = ticketViewModel.ticketFormData.value.seatId
    val seatName = seatNameList.joinToString(separator = ", ")

    val seatNumber = ticketViewModel.ticketFormData.value.seatId.size
    val showTimeState =  showTimeViewModel.getShowTimeById(bookingViewModel.bookingFormData.value.showTimeId).observeAsState(initial = null).value
    val ticketPrice = showTimeState?.price
    val totalSeatPrice = ticketPrice?.times(seatNumber) ?: 0.0
    Log.d("TAG", "totalSeatPrice: "+totalSeatPrice)

    //
    val itemSelected = bookingItemViewModel.bookingItemFormDataList.value
    val totalItems = itemSelected.sumOf { it.quantity }
    val totalBuffetPrice = itemSelected.sumOf { it.price * it.quantity }
    Log.d("TAG", "item Selected List: "+itemSelected)
    // total Amount
    val totalAmount = totalBuffetPrice + totalSeatPrice
    //

    LaunchedEffect(selectedMethodName) {
        paymentViewModel.setMethodFormData(selectedMethodName ?: "")
        paymentViewModel.setStatusFormData(true)
        paymentViewModel.setTransactionDateFormData(currentDate)
        //
        bookingViewModel.setTotalAmountFormData(totalAmount)
        ticketViewModel.setPriceFormData(totalAmount)
        Log.d("TAG", "Payment Form Data: "+paymentViewModel.paymentFormData)
        Log.d("TAG", "Booking Form Data: "+bookingViewModel.bookingFormData)
        Log.d("TAG", "Ticket Form Data: "+ticketViewModel.ticketFormData)
    }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {

        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .navigationBarsPadding()
                    .background(Color(0xff000000).copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
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
                                text = "Movie :",
                                color = Color.Gray,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = showTimeState?.nameMovie.toString(),
                                color = Color.Gray,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        }
                        // seat count
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = "Ticket count :",
                                color = Color.White,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = seatNumber.toString(),
                                color = Color.White,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = "(${totalSeatPrice})",
                                color = Color(0xff33B528),
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        }
                        // section
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = "Section :",
                                color = Color.Gray,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "(${section})",
                                color = Color.Gray,
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
                                text = seatName,
                                color = Color.White,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        }
                        // Buffet Products
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = "Buffet products:",
                                color = Color.Gray,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if(totalItems == 0)"None" else "${totalItems}",
                                color = Color.Gray,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = if(totalBuffetPrice == 0.0) "(0$)" else "($totalBuffetPrice)",
                                color = Color(0xff33B528),
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        }
                        // Movie Theater
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = "Movie theater :",
                                color = Color.White,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "LHB Cinema",
                                color = Color.White,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        }
                    }
                    //
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .width(2.dp)
                            .height(150.dp)
                            .background(Color.White)
                    )
                    //
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Total Amount",
                            color = Color.White,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = if(totalAmount == 0.0)"0$" else "${totalAmount}",
                            color = Color(0xff33B528),
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff14111e))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            imageMethodList.forEachIndexed { index, (imageRes, methodName) ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = if (selectedImage == index) 2.dp else 0.dp,
                            color = if (selectedImage == index) Color.White else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            if (selectedImage == index) {
                                selectedImage = null
                                selectedMethodName = null
                            } else {
                                selectedImage = index
                                selectedMethodName = methodName
                            }
                            Log.d("TAG", "PaymentMethod : ${selectedMethodName}")
                        }
                )
            }
        }
    }
}