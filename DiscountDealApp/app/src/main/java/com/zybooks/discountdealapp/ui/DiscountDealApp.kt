package com.zybooks.discountdealapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zybooks.discountdealapp.ui.screens.*
import com.zybooks.discountdealapp.ui.navigation.Routes
import com.zybooks.discountdealapp.ui.theme.DiscountDealTheme
import com.zybooks.discountdealapp.ui.viewmodels.*

@Composable
fun DiscountDealApp() {
    val profileVM: ProfileViewModel = viewModel()

    DiscountDealTheme(darkTheme = profileVM.darkModeEnabled)  {
        Surface(color = MaterialTheme.colorScheme.background) {

            val navController = rememberNavController()
            val homeVM: HomeViewModel = viewModel()
            val nearbyVM: NearbyDealsViewModel = viewModel()
            val onlineVM: OnlineDealsViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = Routes.Home.route
            ) {
                composable(Routes.Home.route) {
                    HomeScreen(
                        homeVM = homeVM,
                        onNearbyClick = { navController.navigate(Routes.Nearby.route) },
                        onOnlineClick = { navController.navigate(Routes.Online.route) },
                        onProfileClick = { navController.navigate(Routes.Profile.route) },
                        onDealClick = { deal ->
                            navController.navigate(Routes.DealDetails.createRoute(deal.id))
                        }
                    )
                }


                composable(Routes.Nearby.route) {
                    NearbyDealsScreen(
                        nearbyVM = nearbyVM,
                        profileVM = profileVM,
                        deals = nearbyVM.nearbyDeals,
                        onDealClick = { deal ->
                            navController.navigate(Routes.DealDetails.createRoute(deal.id))
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Routes.Online.route) {
                    OnlineDealsScreen(
                        onlineVM = onlineVM,
                        onDealClick = { deal ->
                            navController.navigate(Routes.DealDetails.createRoute(deal.id))
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(Routes.Profile.route) {
                    ProfileScreen(
                        profileVM = profileVM,
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Routes.DealDetails.route,
                    arguments = listOf(navArgument("dealId") { type = NavType.IntType })
                ) { entry ->

                    val dealDetailsVM: DealDetailsViewModel = viewModel(entry)

                    val deal = dealDetailsVM.selectedDeal

                    DealDetailsScreen(
                        deal = deal,
                        onBack = { navController.popBackStack() }
                    )
                }


            }
        }
    }
}
