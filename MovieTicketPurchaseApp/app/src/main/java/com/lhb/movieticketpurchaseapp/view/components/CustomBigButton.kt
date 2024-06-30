package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.lhb.movieticketpurchaseapp.view.navigator.Screens

@Composable
fun CustomBigButton(
    onClick: () -> Unit = {},
    title: String
){
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color("#312064".toColorInt()))
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}