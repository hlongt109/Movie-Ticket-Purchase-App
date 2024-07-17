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
    ManageHallScreen("ManageHallScreen"),
    ManageTimeFrameScreen("ManageTimeFrameScreen"),
    ManageShowTimeOfTheter("ManageShowTimeOfTheter"),
    //
    ADD_MovieGenre_Form("AddMovieGenreForm"),
    UPDATE_MovieGenre_Form("UpdateMovieGenreForm"),
    ADD_Movie_Form("AddMovie"),
    UPDATE_Movie_Form("UpdateMovie"),
    ADD_Theaters_Form("AddTheatersForm"),
    UPDATE_Theaters_Form("UpdateTheaters"),
    ADD_UPDATE_CinemaHall_Form("CinemaHallForm"),
    UPDATE_CinemaHall_Form("UpdateCinema"),
    ADD_ShowTimeForm("AddShowTimeForm"),
    UPDATE_ShowTimeForm("UpdateShowTime"),
    ADD_FoodDrinks_Form("AddFoodDrinksForm"),
    UPDATE_FoodDrinks_Form("UpdateFoodDrinks"),
    ADD_Staff_Form("AddStaffForm"),
    UPDATE_Staff_Form("UpdateStaff"),
    ADD_Tickets_Form("AddTicketsForm"),
    ADD_TimeFrame_Form("AddTimeFrameForm"),
    UPDATE_TimeFrame_Form("UpdateTimeFrame")
}