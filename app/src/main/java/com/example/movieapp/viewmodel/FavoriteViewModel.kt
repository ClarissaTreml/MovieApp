package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie

class FavoriteViewModel: ViewModel() {
    private val _favoriteMovies = mutableStateListOf<Movie>()
    val favoriteMovies: List<Movie>
    get() = _favoriteMovies

    fun addFavoriteMovie(movie: Movie){
        if(!existsFavoriteMovies(movie = movie)){
            _favoriteMovies.add(movie)
        }


    }

    fun removeFavoriteMovie(movie: Movie){

    }

    fun getAllFavoriteMovies(movie: Movie){

    }

    fun existsFavoriteMovies(movie: Movie): Boolean{
        return false
    }
}