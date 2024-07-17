package com.lhb.movieticketpurchaseapp.view.adminScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.view.components.DeleteDialog
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.ItemShowTime
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import kotlinx.coroutines.launch

@Composable
fun ManagementShowTimeOfTheater(
    theaterId: String,
    theaterName: String?,
    showTimeViewModel: ShowTimeViewModel,
    navController: NavController
){
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val showtimeState = showTimeViewModel.getShowTimeListByTheaterId(theaterId).observeAsState(initial = emptyList())
    val showTimeList = showtimeState.value

    var idToDelete by remember { mutableStateOf("") }
    var showDialogDelete by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = theaterName ?: "Manager show time",
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
            FAButton(onClick = { navController.navigate(Screens.ADD_ShowTimeForm.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                showTimeList?.let {
                    items(it.size) { index ->
                        ItemShowTime(
                            showTime = showTimeList[index],
                            onClickToEdit = { id -> navController.navigate("${Screens.UPDATE_ShowTimeForm.route}/${id}") },
                            onClickToDelete = { id ->
                                idToDelete = id
                                showDialogDelete = true
                            }
                        )
                    }
                }
            }
        }
    }
    if (showDialogDelete) {
        DeleteDialog(
            onConfirm = {
                showTimeViewModel.deleteShowTime(id = idToDelete) { success ->
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
