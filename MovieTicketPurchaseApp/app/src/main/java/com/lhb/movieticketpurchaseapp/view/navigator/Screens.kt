package com.lhb.movieticketpurchaseapp.view.navigator

enum class Screens(val route: String) {
    WelcomeScreen("Welcome"),
    LoginScreen("Login"),
    SignUpScreen("SignUp"),
    // nav
    UserBottomTav("UserBottomTav"),
    AdminBottomTav("AdminBottomTav"),
    // admin
    ManageMovieScreen("ManageMovieScreen"),
    ManageMovieGenreScreen("ManageMovieGenreScreen"),
    ManageTheatersScreen("ManageTheatersScreen"),
    ManageCinemaHallScreen("ManageCinemaHallScreen"),
    ManageShowTimeScreen("ManageShowTimeScreen"),
    ManageFoodAndDrinkScreen("ManageFoodAndDrinkScreen"),
    ManageTicketsScreen("ManageTicketsScreen"),
    ManageStaffScreen("ManageStaffScreen"),
    ManageUsersScreen("ManageUsersScreen"),
    ManageMovieReviews("ManageMovieReviews"),
    //
    MovieGenreFormScreen("MovieGenreFormScreen"),
    MovieFormScreen("MovieFormScreen"),
    TheaterFormScreen("TheaterFormScreen"),
    CinemaHallFormScreen("CinemaHallFormScreen"),
    ShowTimeFormScreen("ShowTimeFormScreen"),
    FoodDrinkFormScreen("FoodDrinkFormScreen"),
    StaffFormScreen("StaffFormScreen"),
    TicketFormScreen("TicketFormScreen")

}