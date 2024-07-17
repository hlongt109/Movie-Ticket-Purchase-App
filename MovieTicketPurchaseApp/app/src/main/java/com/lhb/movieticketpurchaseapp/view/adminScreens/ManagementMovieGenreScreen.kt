package com.lhb.movieticketpurchaseapp.view.adminScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.DeleteDialog
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.ItemMovieGenre
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ManagementMovieGenreScreen(
    navController: NavController,
    movieGenreViewModel: MovieGenreViewModel
) {
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val movieGenreListState = movieGenreViewModel.listMovieTypes.observeAsState(initial = emptyList())
    var movieGenreList = movieGenreListState.value

    var idToDelete by remember { mutableStateOf("") }
    var showDialogDelete by remember { mutableStateOf(false) }

    // search
    LaunchedEffect(searchQuery.value) {
        val query = searchQuery.value.toLowerCase(Locale.getDefault())
        val filterList = if (query.isNotEmpty()) {
            movieGenreList.filter { mg ->
                mg.name.toLowerCase(Locale.getDefault())
                    .contains(query) || mg.id.toLowerCase(Locale.getDefault()).contains(query)
            }
        } else {
            movieGenreList
        }
        movieGenreList = filterList
    }
    if (searchQuery.value.isEmpty()) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                movieGenreList = movieGenreListState.value
            }
        }
    }
    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = "Genre Management",
                isSearchActive = isSearchMovie.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = { searchQuery.value = it },
                onSearchActiveChange = {
                    isSearchMovie.value = it
                    searchQuery.value = ""
                },
                onBackClick = { navController.navigate(Screens.AdminBottomTav.route) },
                onSearchIconClick = { isSearchMovie.value = true },
                onMoreIconClick = {}
            )
        },
        floatingActionButton = {
            FAButton(onClick = { navController.navigate(Screens.ADD_MovieGenre_Form.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(movieGenreList.size) { index ->
                    ItemMovieGenre(
                        index = index + 1,
                        movieType = movieGenreList[index],
                        onEditClick = { id -> navController.navigate("${Screens.UPDATE_MovieGenre_Form.route}/${id}") },
                        onDeleteClick = { id ->
                            idToDelete = id
                            showDialogDelete = true
                        },
                    )
                }
            }
        }
    }
    if (showDialogDelete) {
        DeleteDialog(
            onConfirm = {
                movieGenreViewModel.deleteMovieGenre(id = idToDelete) { success ->
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
