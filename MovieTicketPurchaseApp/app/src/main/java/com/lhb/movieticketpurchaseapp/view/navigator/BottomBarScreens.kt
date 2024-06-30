package com.lhb.movieticketpurchaseapp.view.navigator

sealed class BottomBarScreens(val screen: String) {
    // user
    data object UserFavouriteScreen : BottomBarScreens("UserFavouriteScreen")
    data object UserHomeScreen : BottomBarScreens("UserHomeScreen")
    data object UserTicketScreen : BottomBarScreens("UserTicketScreen")
    // admin
    data object AdminDashboard : BottomBarScreens("AdminDashboard")
    data object AdminStatistics : BottomBarScreens("AdminStatistics")
    data object AdminPersonal : BottomBarScreens("AdminPersonal")
}
