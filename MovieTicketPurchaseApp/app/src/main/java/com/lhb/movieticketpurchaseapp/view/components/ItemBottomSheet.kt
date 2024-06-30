package com.lhb.movieticketpurchaseapp.view.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemBottomSheet(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier
){
    Spacer(modifier = Modifier.height(10.dp))
    Row (
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xff14111e),
                shape = RoundedCornerShape(5.dp)
            )
            .padding(15.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(
            Icons.Outlined.AddCircleOutline,
            contentDescription = "",
            modifier = Modifier.size(26.dp),
            tint = Color(0xffffffff)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = title,
            color = Color(0xffffffff),
            fontFamily = Inter,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}