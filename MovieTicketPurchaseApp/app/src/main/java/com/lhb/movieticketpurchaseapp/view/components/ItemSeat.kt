package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.model.Seat
import com.lhb.movieticketpurchaseapp.model.SeatStatus
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemSeat(
    seat: Seat,
    clickable: Boolean = true,
    selectSeatName: (String) -> Unit
){
    var status by remember { mutableStateOf(seat.status) }

    val backgroundColor = when (status){
        SeatStatus.EMPTY -> Color.LightGray.copy(alpha = 0.5f)
        SeatStatus.SELECTED -> Color(0xFFFFA500)
        SeatStatus.BOOKED -> Color(0xff39276f)
        SeatStatus.AISLE -> Color.Transparent
    }
    val borderModifier = if (status != SeatStatus.AISLE){
        Modifier.border(
            BorderStroke(1.dp, Color.DarkGray.copy(alpha = 0.8f)),
            shape = RoundedCornerShape(8.dp)
        )
    }else Modifier
    Box(
        modifier = Modifier
            .padding(2.dp)
            .size(width = 35.dp, height = 30.dp)
            .then(borderModifier)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(if (seat.status != SeatStatus.AISLE) 3.dp else 0.dp)
            .clickable(enabled = clickable && (status == SeatStatus.EMPTY || status == SeatStatus.SELECTED)) {
                status = if (status == SeatStatus.EMPTY){
                    selectSeatName("${seat.row}${seat.number}")
                    SeatStatus.SELECTED
                } else{
                    selectSeatName("")
                    SeatStatus.EMPTY
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if(seat.status != SeatStatus.AISLE){
            Text(text = "${seat.row}${seat.number}",
                fontFamily = Inter,
                fontWeight =  FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.Unspecified
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewSeat() {
//    Row {
//        ItemSeat(Seat("demo1",'X', 1, SeatStatus.EMPTY))
//        ItemSeat(Seat("demo2",'Y', 1, SeatStatus.SELECTED))
//        ItemSeat(Seat("demo3",'Z', 1, SeatStatus.BOOKED))
//    }
//}