package com.lhb.movieticketpurchaseapp.view.adminScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Room
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material.icons.outlined.Theaters
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.navigator.Screens

@Composable
fun DashboardManage(navController: NavController) {
    val menuItems = listOf(
        Triple("Movie Genres", Icons.Outlined.Movie, Screens.ManageMovieGenreScreen.route),
        Triple("Movies", Icons.Outlined.Movie, Screens.ManageMovieScreen.route),
        Triple("Theaters", Icons.Outlined.Room, Screens.ManageTheatersScreen.route),
        Triple("Cinema Halls", Icons.Outlined.Theaters, Screens.ManageCinemaHallScreen.route),
        Triple("Show Times", Icons.Outlined.Schedule, Screens.ManageShowTimeScreen.route),
        Triple("Food & Drinks", Icons.Outlined.Fastfood, Screens.ManageFoodAndDrinkScreen.route),
        Triple("Tickets", Icons.Outlined.ConfirmationNumber, Screens.ManageTicketsScreen.route),
        Triple("Movie Reviews", Icons.Outlined.Stars, Screens.ManageMovieReviews.route),
        Triple("Staff", Icons.Outlined.Groups, Screens.ManageStaffScreen.route),
        Triple("Users", Icons.Outlined.PeopleAlt, Screens.ManageUsersScreen.route),
    )

    LazyColumn(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        items(menuItems.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f)
                            .height(70.dp)
                            .background(Color(0xff14111e), RoundedCornerShape(5.dp))
                            .border(
                                width = 1.dp,
                                color = Color(0xff6C47DB).copy(alpha = 0.7f),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clickable {
                                navController.navigate(item.third)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center ,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Icon(
                                imageVector = item.second,
                                contentDescription = item.first,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = item.first,
                                color = Color(0xffffffff).copy(alpha = 0.95f),
                                fontSize = 16.sp,
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}