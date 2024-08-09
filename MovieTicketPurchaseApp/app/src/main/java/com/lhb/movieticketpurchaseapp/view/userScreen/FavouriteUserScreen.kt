package com.lhb.movieticketpurchaseapp.view.userScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.ItemFavorite
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.FavouriteViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteUserScreen(
    navController: NavController,
    favouriteViewModel: FavouriteViewModel,
    userViewModel: UserViewModel,
    movieViewModel: MovieViewModel
) {
    var isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val userId = userViewModel.getUserId()
    
    LaunchedEffect(userId) {
        favouriteViewModel.getFavouriteListByUserId(userId ?: "")
    }

    var refreshTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(refreshTrigger) {
        favouriteViewModel.getFavouriteListByUserId(userId ?: "")
    }
    val favouriteListState = favouriteViewModel.getFavouriteListByUserId(userId ?: "").observeAsState(initial = emptyList())
    val favouriteList = favouriteListState.value

    Scaffold(
        containerColor = Color(0xff14111e),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 5.dp, end = 5.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (!isSearchMovie.value) {
                    Text(
                        text = "My Favorite",
                        color = Color(0xffffffff),
                        fontSize = 18.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    IconButton(
                        onClick = {
                            isSearchMovie.value = true
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            Icons.Outlined.Search, contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xffffffff)
                        )
                    }
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            Icons.Outlined.MoreVert, contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xffffffff)
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery.value,
                            onValueChange = { searchQuery.value = it },
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                                .weight(1f),
                            placeholder = {
                                Text(
                                    text = "Search ...",
                                    fontSize = 14.sp,
                                    color = Color(0xffffffff),
                                    fontFamily = Inter,
                                    fontWeight = FontWeight.Medium,
                                )
                            },
                            textStyle = TextStyle(
                                color = Color(0xffffffff),
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.White
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        searchQuery.value = ""
                                    }
                                ) {
                                    Icon(
                                        Icons.Outlined.Close, contentDescription = "",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color(0xffffffff)
                                    )
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Color(0xff6C47DB),
                                focusedBorderColor = Color(0xff6C47DB)
                            ),
                            shape = RoundedCornerShape(12.dp),
                        )
                        IconButton(
                            onClick = {
                                isSearchMovie.value = false
                                searchQuery.value = ""
                            }
                        ) {
                            Icon(
                                Icons.Outlined.Cancel, contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xffffffff)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        //
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                favouriteList?.let {
                    items(it.chunked(2)) { rowItems ->
                        Row (modifier = Modifier.fillMaxWidth()){
                            rowItems.forEach{favourite ->
                                ItemFavorite(
                                    movieId = favourite.movieId,
                                    movieViewModel = movieViewModel,
                                    onClickToDelete = {
                                        favouriteViewModel.removeFavorite(favourite.id){result ->
                                            scope.launch {
                                                if(result){
                                                    Toast.makeText(context, "removed ", Toast.LENGTH_SHORT).show()
                                                    refreshTrigger = !refreshTrigger
                                                }else{
                                                    Toast.makeText(context, "failed ", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    },
                                    onClickToDetails = { movie ->
                                        navController.navigate("${Screens.DetailsScreen.route}/${movie.id}")
                                    },
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
}