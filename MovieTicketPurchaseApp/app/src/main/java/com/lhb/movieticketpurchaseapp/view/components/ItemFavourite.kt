package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.model.Movie
import com.lhb.movieticketpurchaseapp.viewmodel.FavouriteViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel

@Composable
fun ItemFavorite(
    movieId: String,
    movieViewModel: MovieViewModel,
    onClickToDelete: () -> Unit,
    onClickToDetails: (movie:Movie) -> Unit,
    modifier: Modifier
){
    val movieState = movieViewModel.getMovieById(movieId).observeAsState(initial = null).value
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color("#000000".toColorInt()).copy(alpha = 0.8f),
            contentColor = Color("#ffffff".toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 4.dp,
            focusedElevation = 4.dp
        ),
        modifier =  modifier
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB), // dark border
                spotColor = Color(0xff6C47DB)  // light border
            )
            .aspectRatio(3 / 4.5f)
            .clickable { movieState?.let { onClickToDetails(it) } }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImage(
                model = movieState?.poster,
                contentDescription = null,
                placeholder = painterResource(R.drawable.img),
                error = painterResource(R.drawable.img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(30.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(0xffffffff))
                    .border(
                        width = 1.dp,
                        color = Color(0xffffffff),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                IconButton(
                    onClick = { onClickToDelete() }
                ) {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = "",
                        tint = Color(0xffFA5353),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}