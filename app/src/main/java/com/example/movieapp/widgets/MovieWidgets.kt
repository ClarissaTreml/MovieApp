package com.example.movieapp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.navigation.MovieScreens

@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit = {},
    onDeleteClick: (Movie) -> Unit = {},
    onAddClick: (Movie) -> Unit = {},
    isHeart: Boolean,
    heartIcon: Boolean) {

    var showInfo by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp) {

        Row() {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(12.dp)) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(movie.images[0])
                                .crossfade(true)
                                .build()),
                        contentDescription = "Movie pic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape))
                }
                Surface(modifier = Modifier.width(215.dp)) {

                    Column() {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = movie.title, style = MaterialTheme.typography.h6)
                        Text(text = "Director: ${movie.director}")
                        Text(text = "Year: ${movie.year}")
                        Spacer(modifier = Modifier.height(10.dp))


                        AnimatedVisibility(
                            visible = showInfo,
                            enter = expandVertically(expandFrom = Alignment.Top),
                            exit = shrinkVertically()) {

                            Column {
                                Text(text = "Plot: ${movie.plot}")

                                Divider(thickness = 1.dp)

                                Text(text = "Genre: ${movie.genre}")
                                Text(text = "Actors: ${movie.actors}")
                                Text(text = "Rating: ${movie.rating}")

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        IconButton(onClick = { showInfo = !showInfo }) {
                            if (showInfo) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "DownArrow")

                            }
                            else {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "UpArrow")
                            }
                        }
                    }
                }
                    if (heartIcon) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End) {

                            FavoriteIcon(
                                movie = movie,
                                onAddClick = { movie ->
                                    onAddClick(movie)
                                },
                                onDeleteClick = { movie ->
                                    onDeleteClick(movie)
                                },
                                isHeart = isHeart)
                        }
                }
            }
        }
    }
}

@Composable
fun HorizontalScrollableImageView(movie: Movie = getMovies()[0]) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 4.dp) {

                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(image)
                            .crossfade(true).build()),
                    contentDescription = "movie image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape))
            }
        }
    }
}

@Composable
fun FavoriteIcon(
    movie: Movie,
    onAddClick: (Movie) -> Unit,
    onDeleteClick: (Movie) -> Unit,
    isHeart: Boolean = false) {

    if (isHeart) {
        IconButton(onClick = {
            onDeleteClick(movie) }) {

            Icon(
                Icons.Default.Favorite, //ohne Füllung
                contentDescription = "FavoriteIcon",
                tint = Color.Cyan)
        }
    }
    else {
        IconButton(onClick = {
            onAddClick(movie) }) {

            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = "FavoriteIcon",
                tint = Color.Cyan)
        }
    }
}

@Composable
fun MovieRowContent(
    navController: NavController,
    movieList: List<Movie>,
    onAddClick: (Movie) -> Unit = {},
    onDeleteClick: (Movie) -> Unit = {},
    isHeart: @Composable (Movie) -> Boolean = { false },
    heartIcon: Boolean) {

    LazyColumn {
        items(movieList) { movie ->
            MovieRow(
                movie = movie,
                onAddClick = { onAddClick(movie) },
                onDeleteClick = {
                    onDeleteClick(movie)
                },
                isHeart = isHeart(movie),
                onItemClick = { movieId ->
                    navController.navigate(route = MovieScreens.DetailScreen.name + "/$movieId")
                },
                heartIcon = heartIcon)
        }
    }
}

@Composable
fun DetailScreenContent(
    movie: Movie,
    onAddClick: (Movie) -> Unit = {},
    onDeleteClick: (Movie) -> Unit = {},
    isHeart: (Movie) -> Boolean,
    heartIcon: Boolean) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

        Column {
            MovieRow(
                movie = movie,
                onAddClick = { onAddClick(movie) },
                onDeleteClick = {
                    onDeleteClick(movie)
                },
                isHeart = isHeart(movie),
                heartIcon = heartIcon)

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Text(text = movie.title, style = MaterialTheme.typography.h5)
            HorizontalScrollableImageView(movie = movie)
        }
    }
}



