package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.Movie
import com.lhb.movieticketpurchaseapp.view.userScreen.ComingSoonList
import com.lhb.movieticketpurchaseapp.view.userScreen.NewMovieList
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel

@Composable
fun MovieCategory(navController: NavController, listMovies: List<Movie>, category: String){
    val filteredMovies = listMovies.filter { it.type == category }
    val newMovies = filteredMovies.filter { it.status == 1 }
    val comingSoonMovies = filteredMovies.filter { it.status == 0 }
    Column {
        filteredMovies.forEach{ movie ->
            // new movie
            NewMovieList(navController, listMovies =  newMovies)
            Spacer(modifier = Modifier.height(15.dp))
            // coming soon
            ComingSoonList(navController, listMovies = comingSoonMovies )
        }
    }
}