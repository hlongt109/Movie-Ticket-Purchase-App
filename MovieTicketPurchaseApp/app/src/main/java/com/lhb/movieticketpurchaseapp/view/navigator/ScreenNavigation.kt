package com.lhb.movieticketpurchaseapp.view.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.ManagerRepository
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
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.MovieFormScreens
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.MovieGenreFormScreen
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModelFactory


@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val retrofitService = RetrofitService()

    val userRepository = UserRepository(retrofitService)
    val loginViewModelFactory = LoginViewModelFactory(userRepository)
    val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)

    val manageRepository = ManagerRepository(retrofitService)
    val movieGenreFactory = MovieGenreViewModelFactory(manageRepository)
    val movieGenreViewModel: MovieGenreViewModel = viewModel(factory = movieGenreFactory)
    val movieFactory = MovieViewModelFactory(manageRepository)
    val movieViewModel: MovieViewModel = viewModel(factory = movieFactory)

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
        composable(Screens.ManageMovieGenreScreen.route) { ManagementMovieGenreScreen(navController,movieGenreViewModel) }
        composable(Screens.ManageMovieScreen.route) { ManagementMovieScreen(navController, movieViewModel) }
        composable(Screens.ManageTheatersScreen.route) { ManagementTheaterScreen(navController) }
        composable(Screens.ManageCinemaHallScreen.route) { ManagementCinemaHallScreen(navController) }
        composable(Screens.ManageShowTimeScreen.route) { ManagementShowTimeScreen(navController) }
        composable(Screens.ManageFoodAndDrinkScreen.route) { ManagementFoodDrinkScreen(navController) }
        composable(Screens.ManageTicketsScreen.route) { ManagementTicketScreen(navController) }
        composable(Screens.ManageMovieReviews.route) { ManagementMovieReviewsScreen(navController) }
        composable(Screens.ManageStaffScreen.route) { ManagementStaffScreen(navController) }
        composable(Screens.ManageUsersScreen.route) { ManagementUserScreen(navController) }
        // form
        composable(Screens.ADD_MovieGenre_Form.route) { MovieGenreFormScreen(idUpdate = "", navController, movieGenreViewModel) }
        composable(
            "${Screens.UPDATE_MovieGenre_Form.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val idUpdate = backStackEntry.arguments?.getString("id")
            idUpdate?.let {
                MovieGenreFormScreen(idUpdate = idUpdate, navController, movieGenreViewModel)
            }
        }
        composable(Screens.ADD_Movie_Form.route){ MovieFormScreens(idUpdate = "", navController, movieViewModel,movieGenreViewModel)}
        composable(
            "${Screens.UPDATE_Movie_Form.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){backStackEntry ->
            val idUpdate = backStackEntry.arguments?.getString("id")
            idUpdate?.let {
                MovieFormScreens(idUpdate = idUpdate, navController , movieViewModel,movieGenreViewModel)
            }
        }

    }
}