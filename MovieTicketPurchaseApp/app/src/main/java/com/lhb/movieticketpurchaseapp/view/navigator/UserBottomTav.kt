package com.lhb.movieticketpurchaseapp.view.navigator

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lhb.movieticketpurchaseapp.view.userScreen.FavouriteUserScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.HomeUserScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.TicketUserScreen
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel

@Composable
fun UserBottomTav(navController: NavController){
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Outlined.Home)
    }
    val movieViewModel: MovieViewModel = viewModel()
    val movieGenreViewModel : MovieGenreViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color("#000000".toColorInt()).copy(alpha = (0.79f)),
                modifier = Modifier
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            ) {
                IconButton(
                    onClick = {
                        selected.value = Icons.Outlined.FavoriteBorder
                        navigationController.navigate(BottomBarScreens.UserFavouriteScreen.screen){
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder, contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Outlined.FavoriteBorder) Color(0xff6C47DB) else Color(0xffffffff)
                    )
                }
                //
                IconButton(
                    onClick = {
                        selected.value = Icons.Outlined.Home
                        navigationController.navigate(BottomBarScreens.UserHomeScreen.screen){
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Outlined.Home, contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Outlined.Home) Color(0xff6C47DB) else Color(0xffffffff)
                    )
                }
                //
                IconButton(
                    onClick = {
                        selected.value = Icons.Outlined.ConfirmationNumber
                        navigationController.navigate(BottomBarScreens.UserTicketScreen.screen){
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Outlined.ConfirmationNumber, contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = if(selected.value == Icons.Outlined.ConfirmationNumber)Color(0xff6C47DB) else Color(0xffffffff)
                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = BottomBarScreens.UserHomeScreen.screen,
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                top = paddingValues.calculateTopPadding(),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr))
        ){
            composable(BottomBarScreens.UserFavouriteScreen.screen){ FavouriteUserScreen(navController)}
            composable(BottomBarScreens.UserHomeScreen.screen){ HomeUserScreen(navController,movieViewModel, movieGenreViewModel)}
            composable(BottomBarScreens.UserTicketScreen.screen){ TicketUserScreen(navController)}
        }
    }
}