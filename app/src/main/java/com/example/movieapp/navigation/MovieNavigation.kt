package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.screens.DetailScreen
import com.example.movieapp.screens.HomeScreen

@Composable
fun MovieNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MovieScreens.Homescreen.name){

        composable(MovieScreens.Homescreen.name){ HomeScreen(navController = navController)}

        composable(
            route = MovieScreens.DetailScreen.name+"/{movieId}",
            arguments = listOf(navArgument(name = "movieId") {
                type = NavType.StringType
            })
        ){backStackEntry -> //das ist unser History aus stacks
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString( "movieId"))
        }
    }

}