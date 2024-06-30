package com.lhb.movieticketpurchaseapp.view.adminScreens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.navigator.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementMovieGenreScreen(navController: NavController) {
    val isSearchMovie = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBar(
                title = "Genre Management",
                isSearchActive = isSearchMovie.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = {searchQuery.value = it},
                onSearchActiveChange = {isSearchMovie.value = it},
                onBackClick = {navController.navigate(Screens.AdminBottomTav.route)},
                onSearchIconClick = { isSearchMovie.value = true },
                onMoreIconClick = {}
            )
        },
        floatingActionButton = {
            FAButton(onClick = { navController.navigate(Screens.MovieGenreFormScreen.route) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }
}
