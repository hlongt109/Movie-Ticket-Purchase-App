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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.lhb.movieticketpurchaseapp.model.TimeFrame
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun CustomChooseFrameTime(
    timeFrameList: List<TimeFrame>,
    onSelectedId: (String) -> Unit,
    initSelectedId: (String) -> Unit
){
    LaunchedEffect(timeFrameList) {
        if (timeFrameList.isNotEmpty()) {
            initSelectedId(timeFrameList[0].id)
        }
    }
    var selected by remember { mutableStateOf(timeFrameList[0].startTime + " : "+ timeFrameList[0].endTime) }

    Text(
        text = "Choose show time:",
        color = Color(0xffffffff),
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(timeFrameList.size) { index ->
            Text(
                text = timeFrameList[index].startTime + " : "+ timeFrameList[index].endTime,
                color = if (timeFrameList[index].startTime + " : "+ timeFrameList[index].endTime == selected) Color(0xff6C47DB) else Color(
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
                        color = if (timeFrameList[index].startTime + " : "+ timeFrameList[index].endTime == selected) Color(0xff6C47DB) else Color(
                            0xff14111e
                        ),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .clickable {
                        selected = timeFrameList[index].startTime + " : "+ timeFrameList[index].endTime
                        onSelectedId(timeFrameList[index].id)
                    }
            )
        }
    }
}