package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.model.Theater
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemTheater(
    theater: Theater,
    onClickToEdit: (id: String) -> Unit,
    onClickToDelete: (id: String) -> Unit
){
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
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB), // dark border
                spotColor = Color(0xff6C47DB)  // light border
            )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Home, contentDescription = "", modifier = Modifier.size(20.dp), tint = Color(0xff6C47DB))
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = theater.name,
                        color = Color.White,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(Icons.Outlined.LocationOn, contentDescription = "", modifier = Modifier.size(20.dp), tint = Color(0xff6C47DB))
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = theater.location,
                        fontFamily = Inter,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(Icons.Outlined.Call, contentDescription = "", modifier = Modifier.size(20.dp), tint = Color(0xff6C47DB))
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = theater.contact,
                        fontFamily = Inter,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xffffffff))
                        .border(
                            width = 1.dp,
                            color = Color(0xffffffff),
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = { onClickToEdit(theater.id) }
                    ) {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = "",
                            tint = Color(0xff6C47DB),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xffffffff))
                        .border(
                            width = 1.dp,
                            color = Color(0xffffffff),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = { onClickToDelete(theater.id) }
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "",
                            tint = Color(0xff6C47DB),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}