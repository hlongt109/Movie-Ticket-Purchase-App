package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.ShowTimeFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.utils.CustomDatePickerRow
import com.lhb.movieticketpurchaseapp.utils.DatePickerRow
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomHallDropDown
import com.lhb.movieticketpurchaseapp.view.components.CustomMovieDropDown
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlineText2
import com.lhb.movieticketpurchaseapp.view.components.CustomTheaterDropDown
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import kotlinx.coroutines.launch

@Composable
fun ShowTimeFormScreen(
    idUpdate: String,
    showTimeViewModel: ShowTimeViewModel,
    movieViewModel: MovieViewModel,
    theaterViewModel: TheaterViewModel,
    cinemaHallViewModel: CinemaHallViewModel,
    timeFrameViewModel: TimeFrameViewModel,
    navController: NavController
){
    val showTimeState = if(idUpdate != "") showTimeViewModel.getShowTimeById(idUpdate).observeAsState(initial = null).value else null
    // theaters
    val theaterState by theaterViewModel.theaterList.observeAsState(initial = emptyList())
    val initialTheaterId = theaterState[0].id
    // movies
    val moviesState by movieViewModel.listMovies.observeAsState(initial = emptyList())
    // time frame
    val timeFramesState = timeFrameViewModel.getAllTimeFrameIds()

    var formData by remember(showTimeState,timeFramesState) {
        mutableStateOf(showTimeState?.toFormData() ?: ShowTimeFormData(theaterId = initialTheaterId, timeFrameId = timeFramesState))
    }
    // halls
    val hallListState by cinemaHallViewModel.getHallByTheaterId(formData.theaterId).observeAsState(initial = emptyList())
    val hallIdsHaveShowTimeState by showTimeViewModel.getHallListHaveShowTime(formData.theaterId,formData.showTimeDate).observeAsState(initial = emptyList())
    val idsWithShowTime = hallIdsHaveShowTimeState.map { it }.toSet()
    val hallListToSelect = hallListState?.filterNot { it.id in idsWithShowTime } ?: emptyList()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    //
    val selectedHallId = remember { mutableStateOf(formData.cinemaHallId) }
    val selectedMovieId = remember { mutableStateOf(formData.movieId) }

    var movieError by remember { mutableStateOf(false) }
    var hallError by remember { mutableStateOf(false) }
    var showTimeDateError by remember { mutableStateOf(false) }
    var theaterIdError by remember { mutableStateOf(false) }
    var timeFrameError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "") "Create Show Time" else "Update Show Time",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            CustomDatePickerRow(
                initialDate = formData.showTimeDate
            ) { selectedDate ->
                formData = formData.copy(showTimeDate = selectedDate)
                Log.d("TAG", "ShowTimeFormScreen: "+formData)
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomTheaterDropDown(
                listTheater = theaterState,
                initialSelectedItemId = initialTheaterId,
                onItemSelected = {selectedId ->
                    formData = formData.copy(theaterId = selectedId)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomHallDropDown(
                listHall = hallListToSelect,
                initialSelectedItemId = selectedHallId.value,
            ) { hallSelected ->
                selectedHallId.value = hallSelected
                formData = formData.copy(cinemaHallId = hallSelected)
            }
            Spacer(modifier = Modifier.height(30.dp))
            CustomMovieDropDown(
                listMovie = moviesState,
                initialSelectedItemId = selectedMovieId.value,
                onNameMovieSelected = {name -> formData = formData.copy(nameMovie = name)},
                onItemSelected = {itemId ->
                    selectedMovieId.value = itemId
                    formData = formData.copy(movieId = itemId)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomOutlineText2(
                value = if(formData.price == 0.0) "" else formData.price.toString(),
                onValueChange = { formData = formData.copy(price = it.toDoubleOrNull() ?: 0.0)},
                icon = Icons.Outlined.AttachMoney,
                label = "Ticket Price",
                isContentError = priceError,
                modifier = Modifier,
                placeholder = "",
                onclick = {}
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(
                title = "SAVE",
                onClick = {
                    if(formData.showTimeDate == ""){
                        showTimeDateError = true
                        Toast.makeText(context, "Show time has not been selected", Toast.LENGTH_SHORT).show()
                        return@CustomBigButton
                    }
                    if(formData.theaterId == ""){
                        theaterIdError = true
                        return@CustomBigButton
                    }
                    if(formData.cinemaHallId == ""){
                        hallError = true
                        return@CustomBigButton
                    }
                    if(formData.movieId == ""){
                        movieError = true
                        return@CustomBigButton
                    }
                    if(formData.price == 0.0){
                        priceError = true
                        return@CustomBigButton
                    }
                    if(formData.timeFrameId.isEmpty()){
                        timeFrameError = true
                        return@CustomBigButton
                    }
                    if(idUpdate == ""){
                        showTimeViewModel.addShowTime(formData){ success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Add showtime successfully", Toast.LENGTH_SHORT).show()
                                    formData = ShowTimeFormData(theaterId = initialTheaterId, timeFrameId = timeFramesState)
                                    selectedHallId.value = ""
                                    selectedMovieId.value = ""
                                }else{
                                    Toast.makeText(context, "Add showtime failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        showTimeViewModel.updateShowTime(idUpdate,formData){success ->
                             coroutineScope.launch {
                                 if(success){
                                     Toast.makeText(context, "Update showtime successfully", Toast.LENGTH_SHORT).show()
                                     navController.popBackStack()
                                 }else{
                                     Toast.makeText(context, "Update showtime failed", Toast.LENGTH_SHORT).show()
                                 }
                             }
                        }
                    }
                }
            )
            Log.d("TAG", "ShowTimeFormScreen: "+formData)
        }
    }
}