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
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.viewmodel.FavoriteViewModel
import com.example.movieapp.widgets.DetailScreenContent

@Composable
fun DetailScreen(
    navController: NavController = rememberNavController(),
    movieId: String? = getMovies()[0].id,
    viewModel: FavoriteViewModel) {

    val movie = filterMovie(movieId = movieId)

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
                            navController.popBackStack() //go back to last screen
                        })
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = movie.title, style = MaterialTheme.typography.h6)
                }
            }
        }) {

        DetailScreenContent(movie = movie,
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

/*@Composable
fun MainContent(movie: Movie) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            MovieRow(movie = movie)
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Text(text = movie.title, style = MaterialTheme.typography.h5)
            HorizontalScrollableImageView(movie = movie)
        }
    }

}*/

fun filterMovie(movieId: String?): Movie {
    return getMovies().filter { movie -> movie.id == movieId }[0]
}