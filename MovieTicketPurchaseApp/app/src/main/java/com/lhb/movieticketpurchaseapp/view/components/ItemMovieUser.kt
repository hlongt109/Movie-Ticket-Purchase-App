package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.model.Movie

@Composable
fun ItemMovieUser(
    movie: Movie,
    onClickToDetails: (movie: Movie) -> Unit
){
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
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB), // dark border
                spotColor = Color(0xff6C47DB)  // light border
            )
    ) {
        AsyncImage(
            model = movie.poster, // Assume movie has a poster URL
            contentDescription = null,
            placeholder = painterResource(R.drawable.img), // Placeholder image
            error = painterResource(R.drawable.img), // Error image
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(0.75f) // Adjust aspect ratio as needed
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Gray) // Placeholder color
        )
    }
}