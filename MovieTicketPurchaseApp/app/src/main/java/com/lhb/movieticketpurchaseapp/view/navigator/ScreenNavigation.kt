package com.lhb.movieticketpurchaseapp.view.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementHallsScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementMovieGenreScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementMovieReviewsScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementMovieScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementShowTimeOfTheater
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementShowTimeScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementStaffScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementTheaterScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementTicketScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementTimeFrameScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.ManagementUserScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.FoodDrinkFormScreens
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.HallFormScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.MovieFormScreens
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.MovieGenreFormScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.ShowTimeFormScreen
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.TheaterFormScreens
import com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens.TimeFrameFormScreens
import com.lhb.movieticketpurchaseapp.view.components.DetailsScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.ChooseFoodDrinkScreen
import com.lhb.movieticketpurchaseapp.view.userScreen.TicketBookingScreen
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.BookingViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.FavouriteViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FavouriteViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModelFactory
import com.lhb.movieticketpurchaseapp.viewmodel.PaymentViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.PaymentViewModelFactory
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


@RequiresApi(Build.VERSION_CODES.O)
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
    val theaterFactory = TheaterViewModelFactory(manageRepository)
    val theaterViewModel: TheaterViewModel = viewModel(factory = theaterFactory)
    val cinemaHallFactory = CinemaHallViewModelFactory(manageRepository)
    val cinemaHallViewModel: CinemaHallViewModel = viewModel(factory = cinemaHallFactory)
    val showTimeFactory = ShowTimeViewModelFactory(manageRepository)
    val showTimeViewModel: ShowTimeViewModel = viewModel(factory = showTimeFactory)
    val timeFrameFactory = TimeFrameModelFactory(manageRepository)
    val timeFrameViewModel: TimeFrameViewModel = viewModel(factory = timeFrameFactory)
    val foodDrinkFactory = FoodDrinkModelFactory(manageRepository)
    val foodDrinkViewModel: FoodDrinkViewModel = viewModel(factory = foodDrinkFactory)
    val userFactory = UserViewModelFactory(userRepository,context)
    val userViewModel: UserViewModel = viewModel(factory = userFactory)
    val bookingFactory = BookingViewModelFactory(userRepository)
    val bookingViewModel: BookingViewModel = viewModel(factory = bookingFactory)
    val bookingItemFactory = BookingItemViewModelFactory(userRepository)
    val bookingItemViewModel: BookingItemViewModel = viewModel(factory = bookingItemFactory)
    val paymentFactory = PaymentViewModelFactory(userRepository)
    val paymentViewModel: PaymentViewModel = viewModel(factory = paymentFactory)
    val ticketFactory = TicketViewModelFactory(userRepository)
    val ticketViewModel: TicketViewModel = viewModel(factory = ticketFactory)
    val favouriteFactory = FavouriteViewModelFactory(userRepository)
    val favouriteViewModel: FavouriteViewModel = viewModel(factory = favouriteFactory)

    NavHost(
        navController = navController,
        startDestination = Screens.WelcomeScreen.route
    ) {
        composable(Screens.WelcomeScreen.route) { WelcomeScreen(navController,userViewModel) }
        composable(Screens.LoginScreen.route) { LoginScreen(navController, userViewModel) }
        composable(Screens.SignUpScreen.route) { SignUpScreen(navController,userViewModel) }
        // bottomNav
        composable(Screens.UserBottomTav.route) { UserBottomTav(navController) }
        composable(Screens.AdminBottomTav.route) { AdminBottomTav(navController) }
        // management
        composable(Screens.ManageMovieGenreScreen.route) { ManagementMovieGenreScreen(navController,movieGenreViewModel) }
        composable(Screens.ManageMovieScreen.route) { ManagementMovieScreen(navController, movieViewModel) }
        composable(Screens.ManageTheatersScreen.route) { ManagementTheaterScreen(navController,theaterViewModel) }
        composable(Screens.ManageCinemaHallScreen.route) { ManagementCinemaHallScreen(navController,cinemaHallViewModel,theaterViewModel) }
        composable(Screens.ManageTimeFrameScreen.route){ ManagementTimeFrameScreen(navController, timeFrameViewModel)}
        composable(Screens.ManageShowTimeScreen.route) { ManagementShowTimeScreen(navController,showTimeViewModel,theaterViewModel) }
        composable(Screens.ManageFoodAndDrinkScreen.route) { ManagementFoodDrinkScreen(navController,foodDrinkViewModel) }
        composable(Screens.ManageTicketsScreen.route) { ManagementTicketScreen(navController) }
        composable(Screens.ManageMovieReviews.route) { ManagementMovieReviewsScreen(navController) }
        composable(Screens.ManageStaffScreen.route) { ManagementStaffScreen(navController) }
        composable(Screens.ManageUsersScreen.route) { ManagementUserScreen(navController) }
        composable(
            "${Screens.ManageHallScreen.route}/{theaterId}/{theaterName}",
            arguments = listOf(
                navArgument("theaterId"){type = NavType.StringType},
                navArgument("theaterName"){type = NavType.StringType}
            )
        ){ backStackEntry ->
            ManagementHallsScreen(
            theaterId = backStackEntry.arguments?.getString("theaterId")?:"",
            nameTheater = backStackEntry.arguments?.getString("theaterName")?:"",
            cinemaHallViewModel = cinemaHallViewModel, navController = navController
        )}
        composable(
            "${Screens.ManageShowTimeOfTheter.route}/{theaterId}/{theaterName}",
            arguments = listOf(
                navArgument("theaterId"){type = NavType.StringType},
                navArgument("theaterName"){type = NavType.StringType}
            )
        ){ backStackEntry ->
            ManagementShowTimeOfTheater(
                theaterId = backStackEntry.arguments?.getString("theaterId")?:"",
                theaterName = backStackEntry.arguments?.getString("theaterName")?:"",
                showTimeViewModel ,
                navController
            )
        }
        composable(
            "${Screens.DetailsScreen.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){ backStackEntry ->
            DetailsScreen(idMovie = backStackEntry.arguments?.getString("id")?:"", navController, movieViewModel,favouriteViewModel,userViewModel)
        }
        // ====== form =======
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
        // movie
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
        // theater
        composable(Screens.ADD_Theaters_Form.route){ TheaterFormScreens(idUpdate = "", navController, theaterViewModel)}
        composable(
            "${Screens.UPDATE_Theaters_Form.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){backStackEntry ->
            val idUpdate = backStackEntry.arguments?.getString("id")
            idUpdate?.let {
                TheaterFormScreens(idUpdate = idUpdate, navController, theaterViewModel)
            }
        }
        // hall
        composable(
            "${Screens.ADD_UPDATE_CinemaHall_Form.route}/{id}/{theaterId}/{theaterName}",
            arguments = listOf(
                navArgument("id"){type = NavType.StringType},
                navArgument("theaterId"){type = NavType.StringType},
                navArgument("theaterName"){type = NavType.StringType}
            )
        ){ backStackEntry ->
            HallFormScreen(
                idUpdate = backStackEntry.arguments?.getString("id")?:"",
                theaterId = backStackEntry.arguments?.getString("theaterId")?:"",
                theaterName =backStackEntry.arguments?.getString("theaterName")?:"",
                cinemaHallViewModel, theaterViewModel, navController
            )
        }
        // showTime
        composable(Screens.ADD_ShowTimeForm.route){ ShowTimeFormScreen(idUpdate = "", showTimeViewModel,movieViewModel,theaterViewModel,cinemaHallViewModel, timeFrameViewModel,navController)}
        composable(
            "${Screens.UPDATE_ShowTimeForm.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){backStackEntry ->
            val idUpdate = backStackEntry.arguments?.getString("id")
            idUpdate?.let { ShowTimeFormScreen(idUpdate = idUpdate, showTimeViewModel,movieViewModel,theaterViewModel,cinemaHallViewModel,timeFrameViewModel,navController) }
        }
        // time frame
        composable(Screens.ADD_TimeFrame_Form.route){ TimeFrameFormScreens(idUpdate = "", navController, timeFrameViewModel)}
        composable(
            "${Screens.UPDATE_TimeFrame_Form.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){ backStackEntry ->
            TimeFrameFormScreens(
                idUpdate = backStackEntry.arguments?.getString("id")?:"",
                navController , timeFrameViewModel )
        }
        // food
        composable(Screens.ADD_FoodDrinks_Form.route){ FoodDrinkFormScreens(idUpdate = "", foodDrinkViewModel, navController)}
        composable(
            "${Screens.UPDATE_FoodDrinks_Form.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){ backStackEntry ->
            FoodDrinkFormScreens(idUpdate = backStackEntry.arguments?.getString("id")?: "", foodDrinkViewModel, navController)
        }
        // booking
        composable(
            "${Screens.BookingScreen.route}/{id}",
            arguments = listOf(navArgument("id"){type = NavType.StringType})
        ){ backStackEntry ->
            TicketBookingScreen(
                idMovie = backStackEntry.arguments?.getString("id")?:"",
                navController, movieViewModel, theaterViewModel, foodDrinkViewModel,showTimeViewModel,bookingViewModel,bookingItemViewModel,timeFrameViewModel,userViewModel,ticketViewModel,paymentViewModel)
        }
        composable(Screens.ChooseItemScreen.route){ ChooseFoodDrinkScreen(navController,foodDrinkViewModel,bookingItemViewModel)}
    }
}