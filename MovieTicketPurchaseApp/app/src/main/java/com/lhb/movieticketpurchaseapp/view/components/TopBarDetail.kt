package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBarDetail(
    onBackClick: () -> Unit = {},
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit = {},
){
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 5.dp, end = 5.dp),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = {onBackClick()},
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.Outlined.ArrowBackIosNew, contentDescription = "",
                modifier = Modifier.size(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xffffffff))
                    .padding(5.dp),
                tint = Color(0xff6C47DB)
            )
        }
        IconButton(
            onClick = {onFavouriteClick()},
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                if(isFavourite)Icons.Default.Favorite else Icons.Outlined.FavoriteBorder, contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = if(isFavourite) Color(0xffFA5353) else  Color(0xffffffff)
            )
        }
    }
}