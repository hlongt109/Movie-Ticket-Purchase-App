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
        "Movie" to Screens.ADD_Movie_Form.route,
        "Movie Genre" to Screens.ADD_MovieGenre_Form.route,
        "Theater" to Screens.ADD_Theaters_Form.route,
        "Cinema Halls" to "${Screens.ADD_UPDATE_CinemaHall_Form.route}/${"empty"}/${"empty"}/${"empty"}",
        "Show Time" to Screens.ADD_ShowTimeForm.route,
        "Food & Drinks" to Screens.ADD_FoodDrinks_Form.route,
        "Staff" to Screens.ADD_Staff_Form.route,
        "Ticket" to Screens.ADD_Tickets_Form.route
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