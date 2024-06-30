package com.lhb.movieticketpurchaseapp.view.navigator

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import com.lhb.movieticketpurchaseapp.view.LoginScreen
import com.lhb.movieticketpurchaseapp.view.SignUpScreen
import com.lhb.movieticketpurchaseapp.view.WelcomeScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementCinemaHallScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementFoodDrinkScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementMovieGenreScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementMovieReviewsScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementMovieScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementShowTimeScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementStaffScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementTheaterScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementTicketScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementUserScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.MovieGenreFormScreen
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
    val retrofitService = RetrofitService()
    val userRepository = UserRepository(retrofitService)
    //
    val loginViewModelFactory = LoginViewModelFactory(userRepository)
    val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
    val movieGenreViewModel: MovieGenreViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.AdminBottomTav.route
    ) {
        composable(Screens.WelcomeScreen.route) { WelcomeScreen(navController) }
        composable(Screens.LoginScreen.route) { LoginScreen(navController, loginViewModel) }
        composable(Screens.SignUpScreen.route) { SignUpScreen(navController) }
        // bottomNav
        composable(Screens.UserBottomTav.route) { UserBottomTav(navController) }
        composable(Screens.AdminBottomTav.route) { AdminBottomTav(navController) }
        // management
        composable(Screens.ManageMovieGenreScreen.route) { ManagementMovieGenreScreen(navController) }
        composable(Screens.ManageMovieScreen.route) { ManagementMovieScreen(navController) }
        composable(Screens.ManageTheatersScreen.route) { ManagementTheaterScreen(navController) }
        composable(Screens.ManageCinemaHallScreen.route) { ManagementCinemaHallScreen(navController) }
        composable(Screens.ManageShowTimeScreen.route) { ManagementShowTimeScreen(navController) }
        composable(Screens.ManageFoodAndDrinkScreen.route) { ManagementFoodDrinkScreen(navController) }
        composable(Screens.ManageTicketsScreen.route) { ManagementTicketScreen(navController) }
        composable(Screens.ManageMovieReviews.route) { ManagementMovieReviewsScreen(navController) }
        composable(Screens.ManageStaffScreen.route) { ManagementStaffScreen(navController) }
        composable(Screens.ManageUsersScreen.route) { ManagementUserScreen(navController) }
        // form
        composable(Screens.MovieGenreFormScreen.route) {
            MovieGenreFormScreen(idUpdate = "", navController, movieGenreViewModel = movieGenreViewModel)
        }
        composable(
            "${Screens.MovieGenreFormScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val idUpdate = backStackEntry.arguments?.getString("id")
            idUpdate?.let {
                MovieGenreFormScreen(
                    idUpdate = idUpdate ?: "", navController, movieGenreViewModel
                )
            }
        }
    }
}