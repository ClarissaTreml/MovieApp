package com.example.movieapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.viewmodel.FavoriteViewModel
import com.example.movieapp.widgets.MovieRowContent

@Composable
fun FavoritesScreen(
    navController: NavController = rememberNavController(),
    viewModel: FavoriteViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Cyan,
                elevation = 3.dp) {
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })

                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "My Favorite Movies", style = MaterialTheme.typography.h6)
                }
            }
        })
    {
        MovieRowContent(navController = navController,
            movieList = viewModel.favoriteMovies,
            onAddClick = { movie ->
                viewModel.addFavorite(movie)
            },
            onDeleteClick = { movie ->
                viewModel.removeFavorite(movie)
            },
            heartIcon = false)
    }
}
