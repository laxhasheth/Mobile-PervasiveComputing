package com.example.moviesearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearchapp.data.model.Movie
import com.example.moviesearchapp.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _movieList = MutableLiveData<List<Movie>?>()
    val movieList: MutableLiveData<List<Movie>?> = _movieList

    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: MutableLiveData<Movie?> = _movieDetails

    fun searchMovies(query: String) {
        viewModelScope.launch {
            val result = repository.searchMovies(query)
            _movieList.value = result
        }
    }

    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            val result = repository.getMovieDetails(imdbID)
            _movieDetails.value = result
        }
    }
}
