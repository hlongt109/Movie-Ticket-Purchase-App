package com.lhb.movieticketpurchaseapp.view.userScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.viewmodel.MovieTypeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeUserScreen(navController: NavController,movieViewModel: MovieViewModel, movieTypeViewModel: MovieTypeViewModel) {
    val isHaveNotice by remember { mutableStateOf(true) }
    val menuType by movieTypeViewModel.listMovieTypes.observeAsState(initial = emptyList())
    val category = listOf("Home") + menuType
    Scaffold(
//        containerColor = Color(0xff171324),
//        containerColor = Color(0xff15121e),
        containerColor = Color(0xff14111e),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.person),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.logo1),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Box{
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xffffffff),
                                    shape = RoundedCornerShape(25.dp)
                                ),
                        ) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center),
                                tint = Color("#ffffff".toColorInt())
                            )
                        }
                        if(isHaveNotice){
                            Box(modifier = Modifier
                                .padding(top = 3.dp)
                                .size(10.dp)
                                .clip(
                                    RoundedCornerShape(5.dp)
                                )
                                .background(Color(0xff6C47DB))
                                .align(Alignment.TopEnd),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xffffffff),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "",
                        tint = Color(0xff6C47DB)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Movie search ...",
                        color = Color(0xff6C47DB),
                        fontSize = 16.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                ) {
                    // banner
                    Banner()
                    Spacer(modifier = Modifier.height(15.dp))
                    // new movie
                    NewMovieList(navController, movieViewModel)
                    Spacer(modifier = Modifier.height(15.dp))
                    // coming soon
                    ComingSoonList(navController, movieViewModel)
                    // thêm lọc loại
                }
            }
            item {
                Spacer(modifier = Modifier.height(65.dp))
            }
        }
    }
}