package com.lhb.movieticketpurchaseapp.view.adminScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.ItemCinemaManage
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ManagementCinemaHallScreen(
    navController: NavController,
    cinemaHallViewModel: CinemaHallViewModel,
    theaterViewModel: TheaterViewModel
){
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val theaterListState = theaterViewModel.theaterList.observeAsState(initial = emptyList())
    var theaterList = theaterListState.value
    // search
    LaunchedEffect(searchQuery.value) {
        val query = searchQuery.value.toLowerCase(Locale.getDefault())
        val filterList = if (query.isNotEmpty()) {
            theaterList.filter { tt ->
                tt.name.toLowerCase(Locale.getDefault())
                    .contains(query) || tt.id.toLowerCase(Locale.getDefault()).contains(query)
            }
        } else {
            theaterList
        }
        theaterList = filterList
    }
    if (searchQuery.value.isEmpty()) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                theaterList = theaterListState.value
            }
        }
    }
    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = "Cinema Hall Management",
                isSearchActive = isSearchMovie.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = {searchQuery.value = it},
                onSearchActiveChange = {isSearchMovie.value = it},
                onBackClick = {navController.navigate(Screens.AdminBottomTav.route)},
                onSearchIconClick = { isSearchMovie.value = true },
                onMoreIconClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(theaterList.size){index ->
                    val theater = theaterList[index]
                    val hallNumber = cinemaHallViewModel.getCinemaHallCountByTheaterId(theater.id)
                    ItemCinemaManage(
                        theater = theater,
                        quantity = hallNumber,
                        titleQuantity = "Hall number :",
                        onClickToShowListHall = {navController.navigate("${Screens.ManageHallScreen.route}/${theater.id}/${theater.name}")}
                    )
                }
            }
        }
    }
}