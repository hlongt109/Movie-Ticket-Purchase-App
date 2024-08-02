package com.lhb.movieticketpurchaseapp.view.userScreen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.lhb.movieticketpurchaseapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun Banner(imageBanner: List<String>) {

    val images = imageBanner.ifEmpty {
        listOf(
            "https://via.placeholder.com/600x400.png?text=Image+1",
            "https://via.placeholder.com/600x400.png?text=Image+2",
            "https://via.placeholder.com/600x400.png?text=Image+3",
            "https://via.placeholder.com/600x400.png?text=Image+4",
            "https://via.placeholder.com/600x400.png?text=Image+5"
        )
    }
    Log.d("TAG", "Banner: "+images.size)
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })
    val scope = rememberCoroutineScope()
    val isUserScrolling = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = true,
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 7.6f),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.lg1),
                error = painterResource(id = R.drawable.lg1)
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            activeColor = Color.White,
            inactiveColor = Color.Gray
        )
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }
            .collect { isScrollInProgress ->
                isUserScrolling.value = isScrollInProgress
            }
    }
// Lướt tự động
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                delay(3000)
                if (!isUserScrolling.value) {
                    val nextPage = (pagerState.currentPage + 1) % images.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.Gray
) {
    val pageCount = pagerState.pageCount
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        for (i in 0 until pageCount) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (pagerState.currentPage == i) activeColor else inactiveColor)
            )
        }
    }
}