package com.lhb.movieticketpurchaseapp.view.userScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.ItemMovie
import com.lhb.movieticketpurchaseapp.view.components.ItemMovieFake
import com.lhb.movieticketpurchaseapp.view.components.ItemSeeAll
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel

@Composable
fun ComingSoonList(navController: NavController, movieViewModel: MovieViewModel) {
    val listMovies by movieViewModel.listMovies.observeAsState(emptyList())
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        Icons.Default.Circle, contentDescription = null, tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Coming Soon",
                        fontSize = 16.sp,
                        color = Color(0xffffffff),
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium
                    )
                }
                TextButton(
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        "See all",
                        color = Color(0xff6C47DB),
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            // movies
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (listMovies.isEmpty()) {
                    items(5) {
                        ItemMovieFake()
                    }
                    item {
                        ItemSeeAll(navController)
                    }
                } else {
                    itemsIndexed(listMovies.take(8)) { index, movie ->
                        ItemMovie(movie = movie, navController)
                    }
                    item {
                        ItemSeeAll(navController)
                    }
                }
            }
        }
    }
}