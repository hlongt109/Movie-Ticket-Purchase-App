package com.lhb.movieticketpurchaseapp.view.staffScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.Ticket
import com.lhb.movieticketpurchaseapp.model.TicketFormData
import com.lhb.movieticketpurchaseapp.model.UserFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.model.toTicketFormData
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlinedTextField
import com.lhb.movieticketpurchaseapp.view.components.ItemTicket
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun CheckTicketScreen(
    navController: NavController,
    ticketViewModel: TicketViewModel,
    userViewModel: UserViewModel,
    bookingViewModel: BookingViewModel,
    showTimeViewModel: ShowTimeViewModel ,
    timeFrameViewModel: TimeFrameViewModel ,
    bookingItemViewModel: BookingItemViewModel,
    foodDrinkViewModel : FoodDrinkViewModel ,
    theaterViewModel: TheaterViewModel
) {
    val isSearchMovie = remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }
    var isSearchQueryError by remember { mutableStateOf(false) }

    var triggerSearch by remember { mutableStateOf(false) }

    val ticketState by ticketViewModel.getTicketByCode(searchQuery).observeAsState(initial = null)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(triggerSearch) {
        if (triggerSearch) {
            ticketViewModel.getTicketByCode(searchQuery)
            triggerSearch = false
        }
    }

    var formData by remember(ticketState) {
        mutableStateOf(ticketState?.toTicketFormData() ?: TicketFormData(status = true))
    }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(title = "Check Ticket", onBackClick = {navController.popBackStack()})
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedTextField(
                value = searchQuery,
                onValueChange = {searchQuery = it},
                icon = Icons.Outlined.Code,
                label = "Enter Ticket Code",
                isContentError = isSearchQueryError,
                errorMessage = "Ticket code must be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(30.dp))
//            CustomBigButton(
//                title = "Check",
//                onClick = {
//                    if(searchQuery == ""){
//                        isSearchQueryError = true
//                        return@CustomBigButton
//                    }else{
//                        isSearchQueryError = false
//                        triggerSearch = true
//                    }
//                }
//            )
            Spacer(modifier = Modifier.height(30.dp))
            if(ticketState != null){
                ItemTicket(
                    ticket = ticketState!!,
                    ticketViewModel,
                    bookingViewModel,
                    showTimeViewModel,
                    timeFrameViewModel,
                    bookingItemViewModel,
                    foodDrinkViewModel,
                    theaterViewModel
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomBigButton(
                    title = "Confirm Ticket",
                    onClick = {
                        ticketViewModel.updateTicket(ticketState?.id ?:"" ,formData){ success ->
                            scope.launch { 
                                if(success){
                                    Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }else{
                                    Toast.makeText(context, "Confirm failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                )
            }else{
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray))
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Nothing",
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
