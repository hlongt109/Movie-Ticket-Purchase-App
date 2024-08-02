package com.lhb.movieticketpurchaseapp.view.userScreen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.BookingFormData
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.TopBarBooking
import com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens.BookingMainScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens.PaymentMethodScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens.PaymentStatusScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens.SelectSeatScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens.createTheaterSeating
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.PaymentViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TicketBookingScreen(
    idMovie: String,
    navController: NavController,
    movieViewModel: MovieViewModel,
    theaterViewModel: TheaterViewModel,
    foodDrinkViewModel: FoodDrinkViewModel,
    showTimeViewModel: ShowTimeViewModel,
    bookingViewModel: BookingViewModel,
    bookingItemViewModel: BookingItemViewModel,
    timeFrameViewModel: TimeFrameViewModel,
    userViewModel: UserViewModel,
    ticketViewModel: TicketViewModel,
    paymentViewModel: PaymentViewModel
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        bookingViewModel.resetFormData()
        ticketViewModel.resetFormData()
        paymentViewModel.resetFormData()
    }

    val context = LocalContext.current
    var paymentStatus by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarBooking(
                onBackClick = { navController.popBackStack() },
                onSelected = { pageSelected ->
                    scope.launch {
                        pagerState.animateScrollToPage(pageSelected)
                    }
                },
                selectedIndex = pagerState.currentPage
            )
        },
        bottomBar = {
            if (pagerState.currentPage != 3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .background(Color(0xff000000).copy(alpha = 0.5f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomBigButton(
                        title = "Next",
                        onClick = {
                            scope.launch {
                                val nextPage = (pagerState.currentPage + 1).coerceAtMost(pagerState.pageCount - 1)
                                pagerState.animateScrollToPage(nextPage)
                                if(pagerState.currentPage == 3){
                                    // save:  payment -> booking -> ticket and bookingItem
                                    paymentViewModel.createPayment(paymentViewModel.paymentFormData.value){result ->
                                        val (success, id) = result
                                        scope.launch {
                                           if(success){
                                               // booking
                                               bookingViewModel.setPaymentIdFormData(id?:"")
                                               bookingViewModel.addBooking(bookingViewModel.bookingFormData.value){result ->
                                                   val (success, id) = result
                                                   scope.launch {
                                                       if(success){
                                                           // ticket and bookingItem
                                                           ticketViewModel.setBookingIdFormData(id?:"")
                                                           bookingItemViewModel.updateBookingIdForAllItems(id?:"")
                                                           ticketViewModel.createTickets(ticketViewModel.ticketFormData.value){result ->
                                                               scope.launch {
                                                                   if(result){
                                                                       paymentStatus = true
                                                                   }else{
                                                                       Toast.makeText(context, "Ticket failed", Toast.LENGTH_SHORT).show()
                                                                   }
                                                               }
                                                           }
                                                           bookingItemViewModel.addAllBookingItems { result ->
                                                               scope.launch {
                                                                   if(result){
                                                                       paymentStatus = true
                                                                   }else{
                                                                       Toast.makeText(context, "Booking Items failed", Toast.LENGTH_SHORT).show()
                                                                   }
                                                               }
                                                           }
                                                       }else{
                                                           Toast.makeText(context, "Booking failed", Toast.LENGTH_SHORT).show()
                                                       }
                                                   }
                                               }
                                           }else{
                                               Toast.makeText(context, "Payment failed", Toast.LENGTH_SHORT).show()
                                           }
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff14111e))
                .padding(bottom = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                when (page) {
                    0 -> BookingMainScreen(
                        id = idMovie,
                        movieViewModel,
                        theaterViewModel,
                        showTimeViewModel,
                        bookingItemViewModel,
                        bookingViewModel,
                        timeFrameViewModel,
                        userViewModel,
                        navController
                    )

                    1 -> SelectSeatScreen(
                        bookingViewModel,
                        showTimeViewModel,
                        userViewModel,
                        ticketViewModel,
                        createTheaterSeating(
                            totalRows = 13,
                            totalSeatsPerRow = 11,
                            aislePositionInRow = 4,
                            aislePositionInColumn = 6
                        ), totalSeatsPerRow = 11,
                    )

                    2 -> PaymentMethodScreen(paymentViewModel,bookingItemViewModel,bookingViewModel,ticketViewModel,showTimeViewModel,timeFrameViewModel)
                    3 -> PaymentStatusScreen(navController,paymentStatus)
                }
            }
        }
    }
}