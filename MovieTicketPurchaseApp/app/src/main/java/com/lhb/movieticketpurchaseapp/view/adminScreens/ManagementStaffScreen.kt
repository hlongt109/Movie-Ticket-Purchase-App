package com.lhb.movieticketpurchaseapp.view.adminScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.lhb.movieticketpurchaseapp.view.components.ItemUser
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ManagementStaffScreen(
    navController: NavController,
    userViewModel: UserViewModel
){
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val userListState = userViewModel.listUser.observeAsState(initial = emptyList())
    var userList = userListState.value.filter { it.role == 1 }

    var idToDelete by remember { mutableStateOf("") }
    var showDialogDelete by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery.value) {
        val query = searchQuery.value.toLowerCase(Locale.getDefault())
        val filterList = if (query.isNotEmpty()) {
            userList.filter { tt ->
                tt.name.toLowerCase(Locale.getDefault())
                    .contains(query) || tt.id.toLowerCase(Locale.getDefault()).contains(query)
            }
        } else {
            userList
        }
        userList = filterList
    }
    if (searchQuery.value.isEmpty()) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                userList = userListState.value.filter { it.role == 1 }
            }
        }
    }

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
            FAButton(onClick = { navController.navigate(Screens.ADD_Staff_Form.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(userList.size){index ->
                    ItemUser(
                        user = userList[index],
                        onEditClick = {id -> navController.navigate("${Screens.UPDATE_Staff_Form.route}/${id}")},
                        onDeleteClick = {id ->
                            idToDelete = id
                            showDialogDelete = true
                        }
                    )

                }
            }
        }
    }
    if (showDialogDelete) {
        DeleteDialog(
            onConfirm = {
                userViewModel.deleteAccount(id = idToDelete) { success ->
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