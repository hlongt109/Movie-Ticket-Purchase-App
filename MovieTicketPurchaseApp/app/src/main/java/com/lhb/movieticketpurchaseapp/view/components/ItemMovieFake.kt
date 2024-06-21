package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lhb.movieticketpurchaseapp.R

@Composable
fun ItemMovieFake(){
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xffD8D8D8)),
    ) {
        AsyncImage(
            model = R.drawable.img,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(5 / 7f)
                .background(Color(0xffDCDCDC)),
        )
    }
}