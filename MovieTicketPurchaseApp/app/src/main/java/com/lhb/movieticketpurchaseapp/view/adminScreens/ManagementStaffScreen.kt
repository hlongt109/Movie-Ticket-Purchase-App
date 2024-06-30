package com.lhb.movieticketpurchaseapp.view.adminScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens

@Composable
fun ManagementStaffScreen(navController: NavController){
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = "Staff Management",
                isSearchActive = isSearchMovie.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = {searchQuery.value = it},
                onSearchActiveChange = {isSearchMovie.value = it},
                onBackClick = {navController.navigate(Screens.AdminBottomTav.route)},
                onSearchIconClick = { isSearchMovie.value = true },
                onMoreIconClick = {}
            )
        },
        floatingActionButton = {
            FAButton(onClick = { navController.navigate(Screens.StaffFormScreen.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}