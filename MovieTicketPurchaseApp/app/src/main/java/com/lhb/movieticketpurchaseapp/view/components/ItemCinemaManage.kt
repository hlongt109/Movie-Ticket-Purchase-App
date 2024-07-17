package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.lhb.movieticketpurchaseapp.model.Theater
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemCinemaManage(
    theater: Theater,
    quantity: Int,
    titleQuantity: String,
    onClickToShowListHall :(id: String) -> Unit
) {
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
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .height(95.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB),
                spotColor = Color(0xff6C47DB)
            )
            .clickable { onClickToShowListHall(theater.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Text(
                text = theater.name,
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Row (
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = titleQuantity,
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.55f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = quantity.toString(),
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}