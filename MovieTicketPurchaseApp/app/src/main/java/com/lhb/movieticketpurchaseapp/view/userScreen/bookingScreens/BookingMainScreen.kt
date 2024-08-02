package com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.ShowTime
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.utils.CustomDatePickerRow
import com.lhb.movieticketpurchaseapp.view.components.CustomButtonSelectItem
import com.lhb.movieticketpurchaseapp.view.components.CustomChooseFrameTime
import com.lhb.movieticketpurchaseapp.view.components.CustomChooseTheater
import com.lhb.movieticketpurchaseapp.view.components.RatingStars
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingMainScreen(
    id: String,
    movieViewModel: MovieViewModel,
    theaterViewModel: TheaterViewModel,
    showTimeViewModel: ShowTimeViewModel,
    bookingItemViewModel: BookingItemViewModel,
    bookingViewModel: BookingViewModel,
    timeFrameViewModel: TimeFrameViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userId = userViewModel.getUserId()

    val timeFrameListState = timeFrameViewModel.timeFrameList.observeAsState(initial = emptyList()).value

    val movieState = if (id != "") movieViewModel.getMovieById(id)
        .observeAsState(initial = null).value else null

    val formData by remember(movieState) {
        mutableStateOf(movieState?.toFormData() ?: MovieFormData())
    }

    val currentDate = SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault()).format(Date())
    var selectedShowTime by remember { mutableStateOf(currentDate) }

    var showTimeState by remember { mutableStateOf<List<ShowTime>>(emptyList()) }

    val showTimeListState by showTimeViewModel.getShowTimeByMovieIdAndDate(id, selectedShowTime)
        .observeAsState(initial = emptyList())


    LaunchedEffect(key1 = selectedShowTime) {
        showTimeState = showTimeListState
        bookingViewModel.setUserIdFormData(userId ?: "")
        bookingViewModel.setBookingDateFormData(selectedShowTime)
        Log.d("TAG", "showTimeState: " + showTimeState)
    }

    // Ensure showTimeState is updated when showTimeListState changes
    LaunchedEffect(key1 = showTimeListState) {
        showTimeState = showTimeListState
        Log.d("TAG", "showTimeListState updated: " + showTimeListState)
    }

    val itemSelected = bookingItemViewModel.bookingItemFormDataList.value
    val totalItems = itemSelected.sumOf { it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
            .padding(top = 50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(4 / 5.5f)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
            ) {
                if (formData.poster.isNotEmpty()) {
                    Image(
                        painter = rememberImagePainter(
                            ImageRequest.Builder(context)
                                .data(formData.poster)
                                .build()
                        ),
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(4 / 5.5f),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Outlined.AddCircleOutline,
                        contentDescription = "",
                        tint = Color(0xffffffff),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = formData.title,
                    fontSize = 24.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xffffffff)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Genre: " + formData.genre,
                    fontSize = 13.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xffffffff)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Duration: " + formData.duration + " mins",
                    fontSize = 13.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xffffffff)
                )
            }
        }
        // show date
        Spacer(modifier = Modifier.height(30.dp))
        CustomDatePickerRow(
            selected = {
                selectedShowTime = it
            },
            initialDate = selectedShowTime,
            onDateSelected = { date ->
                selectedShowTime = date
                bookingViewModel.setBookingDateFormData(date)
            }
        )
        // time frame
        Spacer(modifier = Modifier.height(10.dp))
        CustomChooseFrameTime(
            timeFrameList = timeFrameListState,
            initSelectedId = {timeFrameId ->
                bookingViewModel.setTimeFrameIdFormData(timeFrameId)
                Log.d("TAG", "Selected TimeFrame : ${timeFrameId}")},
            onSelectedId = {timeFrameId ->
                bookingViewModel.setTimeFrameIdFormData(timeFrameId)
                Log.d("TAG", "Selected TimeFrame : ${timeFrameId}")
            }
        )
        // choose cinema
        Spacer(modifier = Modifier.height(10.dp))
        CustomChooseTheater(
            showTimeList = showTimeState,
            theaterViewModel = theaterViewModel,
            onSelectedShowTimeId = {showTimeId->
                bookingViewModel.setShowTimeIdFormData(showTimeId)
                Log.d("TAG", "Selected ShowTime : ${showTimeId}")
            }
        )
        // choose item
        Spacer(modifier = Modifier.height(20.dp))
        CustomButtonSelectItem(
            title = "Buffet Products",
            ItemNumber = totalItems,
            onClick = {navController.navigate(Screens.ChooseItemScreen.route)}
        )
        Log.d("TAG", "BookingFormData: "+bookingViewModel.bookingFormData)
    }
}