package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.lhb.movieticketpurchaseapp.model.CinemaHall
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemHall(
    hall: CinemaHall,
    onClickToEdit:(id: String) -> Unit,
    onClickToDelete: (id: String) -> Unit,
    onClikToDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
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
        modifier = modifier
            .padding(bottom = 8.dp)
            .height(120.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB),
                spotColor = Color(0xff6C47DB)
            )
            .clickable { onClikToDetails(hall.id) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = hall.name,
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = hall.capacity.toString() + " seats",
                    fontSize = 15.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .size(30.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xffffffff))
                            .border(
                                width = 1.dp,
                                color = Color(0xff6C47DB),
                                shape = RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        IconButton(
                            onClick = { onClickToEdit(hall.id) }
                        ) {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = "",
                                tint = Color(0xff6C47DB),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .size(30.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xffffffff))
                            .border(
                                width = 1.dp,
                                color = Color(0xff6C47DB),
                                shape = RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        IconButton(
                            onClick = { onClickToDelete(hall.id) }
                        ) {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = "",
                                tint = Color(0xff6C47DB),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}