package com.lhb.movieticketpurchaseapp.view.navigator

sealed class BottomBarScreens(val screen: String) {
    data object UserFavouriteScreen : BottomBarScreens("UserFavouriteScreen")
    data object UserHomeScreen : BottomBarScreens("UserHomeScreen")
    data object UserTicketScreen : BottomBarScreens("UserTicketScreen")
}
