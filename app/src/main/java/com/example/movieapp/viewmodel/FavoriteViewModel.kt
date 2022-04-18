package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie

class FavoriteViewModel : ViewModel() {
    private val _favoriteMovies = mutableStateListOf<Movie>()
    val favoriteMovies: List<Movie>
        get() = _favoriteMovies

    fun addFavorite(movie: Movie) {
        if (!existFavorite(movie = movie)) {
            _favoriteMovies.add(movie)
        }
    }

    fun removeFavorite(movie: Movie) {
        _favoriteMovies.remove(movie)
    }

    fun existFavorite(movie: Movie): Boolean {
        return _favoriteMovies.any { movies -> movies.id == movie.id }
    }

    fun isFavorite(movie: Movie): Boolean {
        return existFavorite(movie = movie)
    }
}