package com.lhb.movieticketpurchaseapp.view.navigator

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.lhb.movieticketpurchaseapp.network.RetrofitService
import com.lhb.movieticketpurchaseapp.repository.UserRepository
import com.lhb.movieticketpurchaseapp.view.LoginScreen
import com.lhb.movieticketpurchaseapp.view.SignUpScreen
import com.lhb.movieticketpurchaseapp.view.WelcomeScreen
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModelFactory

@Composable
fun ScreenNavigation(){
    val navController = rememberNavController()
    val retrofitService = RetrofitService()
    val userRepository = UserRepository(retrofitService)
    //
    val loginViewModelFactory = LoginViewModelFactory(userRepository)
    val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
    NavHost(
        navController = navController,
        startDestination = Screens.UserBottomTav.route
    ){
        composable(Screens.WelcomeScreen.route){ WelcomeScreen(navController)}
        composable(Screens.LoginScreen.route){ LoginScreen(navController,loginViewModel) }
        composable(Screens.SignUpScreen.route){ SignUpScreen(navController)}
        //
        composable(Screens.UserBottomTav.route){ UserBottomTav(navController)}
    }
}