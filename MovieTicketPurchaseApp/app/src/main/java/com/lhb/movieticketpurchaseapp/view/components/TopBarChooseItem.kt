package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun TopBarChooseItem(
    title: String,
    onBackClick: () -> Unit = {},
    isShowButtonDelete: Boolean = false,
    onClickDeleteAll : () -> Unit = {}
) {
    Box(
        modifier = Modifier
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
                modifier = Modifier.size(24.dp),
                tint = Color(0xffffffff)
            )
        }
        if(isShowButtonDelete){
            IconButton(
                onClick = {onClickDeleteAll()},
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    Icons.Default.Delete, contentDescription = "",
                    modifier = Modifier.size(26.dp),
                    tint = Color(0xffffffff)
                )
            }
        }else{
            Text(
                text = title,
                color = Color(0xffffffff),
                fontSize = 18.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}