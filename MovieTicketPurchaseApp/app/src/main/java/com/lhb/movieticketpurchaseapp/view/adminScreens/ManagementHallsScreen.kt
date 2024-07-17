package com.lhb.movieticketpurchaseapp.view.adminScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.view.components.DeleteDialog
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.ItemHall
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ManagementHallsScreen(
    theaterId: String,
    nameTheater: String?,
    cinemaHallViewModel: CinemaHallViewModel,
    navController: NavController
){
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val hallListState = cinemaHallViewModel.getHallByTheaterId(theaterId).observeAsState(initial = emptyList())
    var hallList = hallListState.value

    var idToDelete by remember { mutableStateOf("") }
    var showDialogDelete by remember { mutableStateOf(false) }

    // search
    LaunchedEffect(searchQuery.value) {
        val query = searchQuery.value.toLowerCase(Locale.getDefault())
        val filterList = if (query.isNotEmpty()) {
            hallList?.filter { item ->
                item.name.toLowerCase(Locale.getDefault()).contains(query) || item.id.toLowerCase(Locale.getDefault()).contains(query)
            }
        } else {
            hallList
        }
        hallList = filterList
    }
    if (searchQuery.value.isEmpty()) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                hallList = hallListState.value
            }
        }
    }
    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = nameTheater ?: "Manager Hall",
                isSearchActive = isSearchMovie.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = {searchQuery.value = it},
                onSearchActiveChange = {isSearchMovie.value = it},
                onBackClick = {navController.popBackStack()},
                onSearchIconClick = { isSearchMovie.value = true },
                onMoreIconClick = {}
            )
        },
        floatingActionButton = {
            FAButton(onClick = { navController.navigate("${Screens.ADD_UPDATE_CinemaHall_Form.route}/${"empty"}/${theaterId}/${nameTheater}") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                hallList?.let {
                    items(it.chunked(3)){ rowItems ->
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowItems.forEach{item ->
                                ItemHall(
                                    hall = item,
                                    onClickToEdit = {id -> navController.navigate("${Screens.ADD_UPDATE_CinemaHall_Form.route}/${id}/${theaterId}/${nameTheater}")},
                                    onClickToDelete = {id ->
                                        idToDelete = id
                                        showDialogDelete = true
                                    },
                                    onClikToDetails = {id -> },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
        if (showDialogDelete) {
            DeleteDialog(
                onConfirm = {
                    cinemaHallViewModel.deleteCinemaHall(id = idToDelete) { success ->
                        scope.launch {
                            if(success){
                                Toast.makeText(context, "Success to delete", Toast.LENGTH_SHORT).show()
                                showDialogDelete = false

                                idToDelete = ""
                            }else{
                                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                                showDialogDelete = false
                            }
                        }
                    }
                },
                onDismiss = {
                    idToDelete = ""
                    showDialogDelete = false
                }
            )
        }
    }
}

