package com.lhb.movieticketpurchaseapp.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.model.ShowTime
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel

@Composable
fun CustomChooseTheater(
    showTimeList: List<ShowTime>,
    theaterViewModel: TheaterViewModel,
    onSelectedShowTimeId: (String) -> Unit
) {
    val idTheaters = showTimeList.map { it.theaterId }

    val theaterList = theaterViewModel.getTheaterByIds(idTheaters).observeAsState(initial = emptyList()).value

    val initialTheaterName = if (theaterList.isNotEmpty()) theaterList[0].name else ""
    var selectedTheater by remember { mutableStateOf(initialTheaterName) }

    val hasTheater = theaterList.isNotEmpty()

    Text(
        text = "Choose a cinema:",
        color = Color(0xffffffff),
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )

    if (hasTheater) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(theaterList.size) { index ->
                Text(
                    text = theaterList[index].name,
                    color = if (theaterList[index].name == selectedTheater) Color(0xff6C47DB) else Color(
                        0xff14111e
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xffffffff))
                        .border(
                            width = 2.dp,
                            color = if (theaterList[index].name == selectedTheater) Color(0xff6C47DB) else Color(
                                0xff14111e
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .clickable {
                            selectedTheater = theaterList[index].name
                            val selectedShowTime = showTimeList.find { it.theaterId == theaterList[index].id }
                            Log.d("TAG", "onSelectedShowTime: "+selectedShowTime)
                            if (selectedShowTime != null) {
                                Log.d("TAG", "Selected ShowTime ID: ${selectedShowTime.id}")
                                onSelectedShowTimeId(selectedShowTime.id)
                            }
                        }
                )
            }
        }
    } else {
        Text(
            text = "No available movie theaters.",
            color = Color(0xff6C47DB),
            fontFamily = Inter,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

}