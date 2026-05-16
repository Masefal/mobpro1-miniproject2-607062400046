package com.masefal_0046.tigaunifess.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.masefal_0046.tigaunifess.ui.screen.DetailScreen
import com.masefal_0046.tigaunifess.ui.screen.MainScreen
import com.masefal_0046.tigaunifess.ui.screen.TrashScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination =Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_TELUFESS) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_TELUFESS)
            DetailScreen(navController, id)
        }
        composable(route = Screen.Trash.route) {
            TrashScreen(navController)
        }
    }
}