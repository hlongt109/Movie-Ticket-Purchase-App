package com.lhb.movieticketpurchaseapp.view.components

import android.net.Uri
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.lhb.movieticketpurchaseapp.model.FavouriteFormData
import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.FavouriteViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    idMovie: String,
    navController: NavController,
    movieViewModel: MovieViewModel,
    favouriteViewModel: FavouriteViewModel,
    userViewModel: UserViewModel
) {
    val userId = userViewModel.getUserId().toString()
    val movieState = if (idMovie != "") movieViewModel.getMovieById(idMovie)
        .observeAsState(initial = null).value else null

    var refreshTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(movieState, refreshTrigger) {
        favouriteViewModel.getFavourite(movieState?.id ?: "", userId)
    }

    LaunchedEffect(refreshTrigger) {
        favouriteViewModel.getFavourite(movieState?.id ?: "", userId)
    }

    val getFavorite by favouriteViewModel.getFavourite(movieState?.id ?: "", userId).observeAsState(initial = null)

    val formData by remember(movieState) {
        mutableStateOf(movieState?.toFormData() ?: MovieFormData())
    }

    var favouriteFormData by remember(movieState) {
        mutableStateOf(FavouriteFormData(userId = userId, movieId = movieState?.id ?: ""))
    }

    var expanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarDetail(
                isFavourite = getFavorite != null,
                onBackClick = { navController.popBackStack() },
                onFavouriteClick = {
                    coroutineScope.launch {
                        if (getFavorite != null) {
                            favouriteViewModel.removeFavorite(getFavorite?.id ?: "") { result ->
                                if (result) {
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                                    refreshTrigger = !refreshTrigger
                                } else {
                                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            favouriteViewModel.addFavorite(favouriteFormData) { result ->
                                if (result) {
                                    Toast.makeText(
                                        context,
                                        "Add favourite successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    refreshTrigger = !refreshTrigger
                                } else {
                                    Toast.makeText(context, "Add favourite failed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(Color(0xff000000).copy(alpha = 0.5f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomBigButton(
                    title = "Buy Ticket Now",
                    onClick = {
                        navController.navigate("${Screens.BookingScreen.route}/${formData.id}")
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff14111e))
                .padding(bottom = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xff14111e))
                    .aspectRatio(16 / 9f),
                contentAlignment = Alignment.Center,
            ) {
                if (formData.trailer != "") {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                setVideoURI(Uri.parse(formData.trailer))
                                setOnPreparedListener {
                                    it.isLooping = true
                                    start()
                                }
                            }
                        },
                        modifier = Modifier
                            .aspectRatio(16 / 9f)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Icon(
                        Icons.Outlined.OndemandVideo,
                        contentDescription = "",
                        tint = Color(0xffffffff),
                        modifier = Modifier.size(50.dp),
                    )
                }
            }
            // header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .offset(y = (-100).dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(140.dp)
                        .aspectRatio(9 / 13.5f),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    if (formData.poster.isNotEmpty()) {
                        Image(
                            painter = rememberImagePainter(
                                ImageRequest.Builder(context)
                                    .data(formData.poster)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.aspectRatio(9 / 13.5f),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Outlined.AddCircleOutline,
                            contentDescription = "",
                            tint = Color(0xffffffff),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                //
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 15.dp)
                ) {
                    Text(
                        text = formData.title,
                        fontSize = 24.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xffffffff)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Genre: " + formData.genre,
                        fontSize = 13.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xffffffff)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Director: " + formData.director,
                        fontSize = 13.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xffffffff)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Duration: " + formData.duration + " mins",
                        fontSize = 13.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xffffffff)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingStars(rating = formData.rating)
                        Text(
                            text = "(${formData.rating}/5)",
                            fontSize = 12.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Light,
                            color = Color(0xffffffff),
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }
            // describe
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .offset(y = (-100).dp)
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
                        "Describe",
                        fontSize = 16.sp,
                        color = Color(0xffffffff),
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = formData.description,
                    fontSize = 15.sp,
                    color = Color(0xffffffff),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    maxLines = if (expanded) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis
                )
                if (formData.description.isNotEmpty()) {
                    Text(
                        text = if (expanded) "See Less" else "See More",
                        color = Color(0xff6C47DB),
                        fontSize = 15.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .clickable { expanded = !expanded }
                    )
                }
            }
            // Images From the Movie
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .offset(y = (-100).dp)
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
                        "Images From the Movie",
                        fontSize = 16.sp,
                        color = Color(0xffffffff),
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 7.5f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center,
                ) {
                    ImageGallery(images = formData.images)
                }
            }
        }
    }
}

@Composable
fun RatingStars(rating: Double) {
    val fullStar = Icons.Outlined.Star
    val emptyStar = Icons.Outlined.StarBorder
    val startHalf = Icons.Outlined.StarHalf
    val starColor = Color(0xffffd700)
    val grayColor = Color(0xffcccccc)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for (i in 1..5) {
            val imageVector = when {
                i <= rating -> fullStar
                i - rating < 1 -> startHalf
                else -> emptyStar
            }
            Icon(
                imageVector = imageVector,
                contentDescription = "Star",
                tint = if (i <= rating || i - rating < 1) starColor else grayColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ImageGallery(images: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 7.5f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xff14111e)),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(images.size) { index ->
                Image(
                    painter = rememberImagePainter(images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(16 / 7.5f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}