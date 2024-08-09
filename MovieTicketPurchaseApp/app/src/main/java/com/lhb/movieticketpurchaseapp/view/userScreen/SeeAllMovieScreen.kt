package com.lhb.movieticketpurchaseapp.view.userScreen

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
import com.lhb.movieticketpurchaseapp.view.ItemMovieSeeAll
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun SeeAllMovieScreen(
    status: Int,
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val movieListState = movieViewModel.listMovies.observeAsState(initial = emptyList())
    var movieList = movieListState.value.filter { it.status == status }

    LaunchedEffect(searchQuery.value) {
        val query = searchQuery.value.toLowerCase(Locale.getDefault())
        val filterList = if (query.isNotEmpty()) {
            movieList.filter { tt ->
                tt.title.toLowerCase(Locale.getDefault())
                    .contains(query) || tt.id.toLowerCase(Locale.getDefault()).contains(query)
            }
        } else {
            movieList
        }
        movieList = filterList
    }
    if (searchQuery.value.isEmpty()) {
        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.IO) {
                movieList = movieListState.value.filter { it.status == status }
            }
        }
    }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = if(status == 0) "Coming Soon Movies" else "New Movies",
                isSearchActive = isSearchMovie.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = { searchQuery.value = it },
                onSearchActiveChange = { isSearchMovie.value = it },
                onBackClick = { navController.navigate(Screens.UserBottomTav.route) },
                onSearchIconClick = { isSearchMovie.value = true },
                onMoreIconClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(movieList.chunked(2)){rowItem ->
                    Row (modifier = Modifier.fillMaxWidth()){
                        rowItem.forEach{movie ->
                            ItemMovieSeeAll(
                                movie = movie,
                                onCLickToDetails = {id -> navController.navigate("${Screens.DetailsScreen.route}/${id}")},
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                            )
                        }
                        if (rowItem.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
