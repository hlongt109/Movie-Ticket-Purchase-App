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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.model.FoodAndDrink
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemFoodDrink(
    foodDrink: FoodAndDrink,
    onClickToEdit: (id: String) -> Unit,
    onClickToDelete: (id: String) -> Unit
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
            .height(130.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB),
                spotColor = Color(0xff6C47DB)
            )
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = foodDrink.image,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img),
                error = painterResource(R.drawable.img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1 / 1.4f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = foodDrink.name,
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = foodDrink.price.toString() + " vnd",
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
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
                        onClick = { onClickToEdit(foodDrink.id) }
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
                        .padding(5.dp)
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
                        onClick = { onClickToDelete(foodDrink.id) }
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