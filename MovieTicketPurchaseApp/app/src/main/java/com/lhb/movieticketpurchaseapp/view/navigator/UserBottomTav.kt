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
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import com.lhb.movieticketpurchaseapp.view.userScreen.FavouriteUserScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.HomeUserScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.TicketUserScreen
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.ShowTimeViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModelFactory

@Composable
fun UserBottomTav(navController: NavController){
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Outlined.Home)
    }
    val retrofitService = RetrofitService()

    val userRepository = UserRepository(retrofitService)
    val manageRepository = ManagerRepository(retrofitService)

    val movieViewModel: MovieViewModel = viewModel()
    val movieGenreViewModel : MovieGenreViewModel = viewModel()

    val ticketViewModelFactory = TicketViewModelFactory(repository = userRepository)
    val ticketViewModel: TicketViewModel = viewModel(factory = ticketViewModelFactory)
    val bookingFactory = BookingViewModelFactory(userRepository)
    val bookingViewModel: BookingViewModel = viewModel(factory = bookingFactory)
    val showTimeFactory = ShowTimeViewModelFactory(manageRepository)
    val showTimeViewModel: ShowTimeViewModel = viewModel(factory = showTimeFactory)
    val timeFrameFactory = TimeFrameModelFactory(manageRepository)
    val timeFrameViewModel: TimeFrameViewModel = viewModel(factory = timeFrameFactory)
    val foodDrinkFactory = FoodDrinkModelFactory(manageRepository)
    val foodDrinkViewModel: FoodDrinkViewModel = viewModel(factory = foodDrinkFactory)
    val theaterFactory = TheaterViewModelFactory(manageRepository)
    val theaterViewModel: TheaterViewModel = viewModel(factory = theaterFactory)
    val bookingItemFactory = BookingItemViewModelFactory(userRepository)
    val bookingItemViewModel: BookingItemViewModel = viewModel(factory = bookingItemFactory)
    val userFactory = UserViewModelFactory(userRepository,context)
    val userViewModel: UserViewModel = viewModel(factory = userFactory)

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
            composable(BottomBarScreens.UserTicketScreen.screen){ TicketUserScreen(navController, ticketViewModel,
                bookingViewModel,
                showTimeViewModel,
                timeFrameViewModel,
                bookingItemViewModel,
                foodDrinkViewModel,
                theaterViewModel,
                userViewModel
            )}
        }
    }
}