package com.example.movieapp.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.models.getMovies
import com.example.movieapp.navigation.MovieScreens
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodel.FavoriteViewModel
import com.example.movieapp.widgets.MovieRowContent

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: FavoriteViewModel){

    var showMenu by remember{
        mutableStateOf(false)
    }

    MovieAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Movies") },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu}) {
                            Icon(imageVector = Icons.Default.MoreVert, 
                                contentDescription = "More")
                        }
                        DropdownMenu(expanded = showMenu, 
                            onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(onClick = { navController.navigate(route = MovieScreens.FavoritesScreen.name) }) {
                                Row{
                                    Icon(imageVector = Icons.Default.Favorite,
                                        contentDescription = "my favorites",
                                        modifier = Modifier.padding(4.dp))

                                    Text(text = "Favorites",
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .width(100.dp))
                                }
                            }
                        }
                    })
            }
        ){
            MovieRowContent(navController = navController,
                getMovies(),
                onAddClick = { movie ->
                    viewModel.addFavorite(movie)
                },
                onDeleteClick = { movie ->
                    viewModel.removeFavorite(movie)
                },
                isHeart = { movie ->
                    viewModel.isFavorite(movie = movie)
                },
                heartIcon = true)
        }
    }
}
