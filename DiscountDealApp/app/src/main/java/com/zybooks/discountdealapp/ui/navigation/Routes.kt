package com.zybooks.discountdealapp.ui.navigation

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object Nearby : Routes("nearby")
    object Online : Routes("online")
    object Profile : Routes("profile")

    object DealDetails : Routes("deal_details/{dealId}") {
        fun createRoute(dealId: Int) = "deal_details/$dealId"
    }
}
