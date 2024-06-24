package com.lhb.movieticketpurchaseapp.view.userScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRows() {
    val tabItems = listOf(
        TabItems(
            title = "Tickets",
            unselectedIcons = Icons.Outlined.ConfirmationNumber,
            selectedIcon = Icons.Outlined.ConfirmationNumber
        ),
        TabItems(
            title = "History",
            unselectedIcons = Icons.Outlined.History,
            selectedIcon = Icons.Outlined.History
        )
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp)
                        .background(Color(0xffffffff))
                )
            }
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
//                    text = {
//                        Text(
//                            text = item.title,
//                            color = if(index == selectedTabIndex) Color("#6C47DB".toColorInt()) else Color("#ffffff".toColorInt())
//                        )
//                    },
                    icon = {
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = item.title,
                            tint = if (index == selectedTabIndex) Color("#6C47DB".toColorInt()) else Color(
                                0xffffffff
                            )
                        )
                    },
                    modifier = Modifier
                        .background (Color(0xff14111e)),
                )
            }
        }
        // nếu tabrow nhiều item có thể dùng ScrollableTabRow cho phép vuốt tabrow
//        ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
//            tabItems.forEachIndexed { index, item ->
//                Tab(
//                    selected = index == selectedTabIndex,
//                    onClick = {
//                        selectedTabIndex = index
//                    },
////                    text = {
////                        Text(text = item.title)
////                    },
//                    icon = {
//                        Icon(
//                            imageVector = if(index == selectedTabIndex) item.selectedIcon else item.unselectedIcons,
//                            contentDescription = item.title
//                        )
//                    }
//                )
//            }
//        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xff14111e))
                .weight(1f)
        ) { index ->
            when(index){
                0 -> TicketsScreen()
                1 -> TicketsHistoryScreen()
            }
        }
    }

}
@Composable
fun TicketsScreen() {
    // Nội dung của màn hình danh sách Tickets
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Danh sách Tickets", color = Color.White)
    }
}

@Composable
fun TicketsHistoryScreen() {
    // Nội dung của màn hình Tickets History
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Tickets History", color = Color.White)
    }
}
data class TabItems(
    val title: String,
    val unselectedIcons: ImageVector,
    val selectedIcon: ImageVector
)