package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemSeeAll(
    onClickToSeeAll: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(190.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xff14111e))
            .clickable { onClickToSeeAll()}
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.ArrowCircleRight,
                contentDescription = null,
                tint = Color(0xffffffff),
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                "See All",
                color = Color(0xffffffff),
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}