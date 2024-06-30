package com.lhb.movieticketpurchaseapp.view.navigator

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.view.components.ItemBottomSheet

@Composable
fun BottomSheetMenu(navController: NavController) {
    val menuItems = listOf(
        "Movie" to Screens.MovieFormScreen.route,
        "Movie Genre" to Screens.MovieGenreFormScreen.route,
        "Theater" to Screens.TheaterFormScreen.route,
        "Cinema Halls" to Screens.CinemaHallFormScreen.route,
        "Show Time" to Screens.ShowTimeFormScreen.route,
        "Food & Drinks" to Screens.FoodDrinkFormScreen.route,
        "Staff" to Screens.StaffFormScreen.route,
        "Ticket" to Screens.TicketFormScreen.route
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        item {
            ItemBottomSheet(
                title = menuItems[0].first,
                onClick = { navController.navigate(menuItems[0].second) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        items(menuItems.drop(1).chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    ItemBottomSheet(
                        title = item.first,
                        onClick = { navController.navigate(item.second) },
                        modifier = Modifier.weight(1f).padding(4.dp)
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}