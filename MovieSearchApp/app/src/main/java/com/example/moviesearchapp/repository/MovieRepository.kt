package com.example.moviesearchapp.repository

import android.util.Log
import com.example.moviesearchapp.data.api.RetrofitInstance
import com.example.moviesearchapp.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {

    private val apiKey = "f7c44cdb" // OMDB API KEY

    // Function to search movies based on query
    suspend fun searchMovies(query: String): List<Movie>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.apiService.searchMovies(query, apiKey)
                if (response.isSuccessful) {
                    response.body()?.Search // ✅ returns List<Movie>
                } else {
                    Log.e("MovieRepository", "API Error (searchMovies): ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("MovieRepository", "Network Error (searchMovies): ${e.message}", e)
                null
            }
        }
    }

    // Function to get movie details by IMDb ID
    suspend fun getMovieDetails(imdbID: String): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.apiService.getMovieDetails(imdbID, apiKey)
                if (response.isSuccessful) {
                    response.body() // ✅ returns a single Movie object
                } else {
                    Log.e("MovieRepository", "API Error (getMovieDetails): ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("MovieRepository", "Network Error (getMovieDetails): ${e.message}", e)
                null
            }
        }
    }
}
