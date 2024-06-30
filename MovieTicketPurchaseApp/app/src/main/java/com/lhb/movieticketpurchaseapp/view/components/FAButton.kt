package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FAButton(
    onClick: () -> Unit = {}
){
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = Color(0xffffffff),
        contentColor = Color(0xff14111e),
        modifier = Modifier.size(50.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xff14111e) )
    }
}