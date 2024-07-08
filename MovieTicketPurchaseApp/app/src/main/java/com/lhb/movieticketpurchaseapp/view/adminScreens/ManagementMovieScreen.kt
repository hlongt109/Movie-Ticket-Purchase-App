package com.lhb.movieticketpurchaseapp.view.adminScreens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.Movie
import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.ItemMovie
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ManagementMovieScreen(navController: NavController, movieViewModel: MovieViewModel) {
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val moviesListeState = movieViewModel.listMovies.observeAsState(initial = emptyList())
    var moviesList = moviesListeState.value

    var idToDelete by remember { mutableStateOf("") }
    var showDialogDelete by remember { mutableStateOf(false) }
    var movieToShowDetails by remember { mutableStateOf<Movie?>(null) }
    var showDialogDetails by remember { mutableStateOf(false) }
    // search
    LaunchedEffect(searchQuery.value) {
        val query = searchQuery.value.toLowerCase(Locale.getDefault())
        val filterList = if (query.isNotEmpty()) {
            moviesList.filter { mv ->
                mv.title.toLowerCase(Locale.getDefault())
                    .contains(query) || mv.id.toLowerCase(Locale.getDefault()).contains(query)
            }
        } else {
            moviesList
        }
        moviesList = filterList
    }
    if (searchQuery.value.isEmpty()) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                moviesList = moviesListeState.value
            }
        }
    }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = "Movie Management",
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
            FAButton(onClick = { navController.navigate(Screens.ADD_Movie_Form.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(moviesList.chunked(2)) { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        rowItems.forEach{movie ->
                            ItemMovie(
                                movie = movie,
                                onClickToEdit = { id -> navController.navigate("${Screens.UPDATE_Movie_Form.route}/${id}") },
                                onClickToDelete = { id -> idToDelete = id },
                                onClickToDetails = { movieToShowDetails = movie },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            )
                        }
                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}