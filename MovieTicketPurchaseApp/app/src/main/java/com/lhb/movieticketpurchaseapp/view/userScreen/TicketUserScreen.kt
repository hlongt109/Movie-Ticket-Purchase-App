package com.lhb.movieticketpurchaseapp.view.userScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel

@Composable
fun TicketUserScreen(
    navController: NavController,
    ticketViewModel: TicketViewModel,
    bookingViewModel: BookingViewModel,
    showTimeViewModel: ShowTimeViewModel,
    timeFrameViewModel: TimeFrameViewModel,
    bookingItemViewModel: BookingItemViewModel,
    foodDrinkViewModel: FoodDrinkViewModel,
    theaterViewModel: TheaterViewModel,
    userViewModel: UserViewModel
) {

    Scaffold(
        containerColor = Color(0xff14111e),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(15.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "My Ticket",
                    color = Color(0xffffffff),
                    fontSize = 18.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        Icons.Outlined.Search, contentDescription = "delete favorite",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xffffffff)
                    )
                }

            }
        }
    ) { innerPadding ->
        //
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TabRows(
                ticketViewModel,
                bookingViewModel,
                showTimeViewModel,
                timeFrameViewModel,
                bookingItemViewModel,
                foodDrinkViewModel,
                theaterViewModel,
                userViewModel)
        }
    }
}