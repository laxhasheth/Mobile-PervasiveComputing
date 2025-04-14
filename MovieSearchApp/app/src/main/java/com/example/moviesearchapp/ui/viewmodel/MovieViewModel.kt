package com.example.moviesearchapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearchapp.data.model.Movie
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val firebaseViewModel = FirebaseViewModel()

    private val _movieList = MutableLiveData<List<Movie>?>()
    val movieList: MutableLiveData<List<Movie>?> = _movieList

    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: MutableLiveData<Movie?> = _movieDetails

    // Search for movies using an external API (e.g., OMDB)
    fun searchMovies(query: String) {
        viewModelScope.launch {
            // You can still search external movie databases like OMDB here if needed
            // For now, I'm assuming you're using Firebase to store movies
            // You could leave the API call here or use the Firebase database for this query
        }
    }

    // Fetch movie details
    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            // If you're using Firebase for movie details, fetch from Firebase
            // Else, you can keep using an external API here
        }
    }

    // Fetch movies from Firebase
    fun fetchMovies() {
        firebaseViewModel.loadMovies()
        firebaseViewModel.movies.observeForever { movies ->
            _movieList.value = movies
        }
    }

    // Add or update movie in Firebase
    fun addOrUpdateMovie(movie: Movie) {
        firebaseViewModel.addOrUpdateMovie(movie)
    }

    // Delete movie from Firebase
    fun deleteMovie(documentId: String) {
        firebaseViewModel.deleteMovie(documentId)
    }
}
