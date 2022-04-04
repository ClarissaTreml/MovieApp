package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.screens.DetailScreen
import com.example.movieapp.screens.FavoritesScreen
import com.example.movieapp.screens.HomeScreen
import com.example.movieapp.viewmodel.FavoriteViewModel

@Composable
fun MovieNavigation(){
    val navController = rememberNavController()
    val favoriteViewModel: FavoriteViewModel = viewModel()

    NavHost(navController = navController, startDestination = MovieScreens.Homescreen.name){

        composable(MovieScreens.Homescreen.name){ HomeScreen(navController = navController, viewModel = favoriteViewModel)}

        composable(
            route = MovieScreens.DetailScreen.name+"/{movieId}",
            arguments = listOf(navArgument(name = "movieId") {
                type = NavType.StringType
            })
        ){backStackEntry -> //das ist unser History aus stacks
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString( "movieId"),
                viewModel = favoriteViewModel)
        }
        composable(route = MovieScreens.FavoritesScreen.name){
            FavoritesScreen(navController = navController, viewModel = favoriteViewModel)
    }

    }
}