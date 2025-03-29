package com.example.moviesearchapp.repository

import android.util.Log
import com.example.moviesearchapp.data.api.RetrofitInstance
import com.example.moviesearchapp.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {

    private val apiKey = "f7c44cdb" // OMB API KEY

    // Function to search movies based on query
    suspend fun searchMovies(query: String): List<Movie>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.apiService.searchMovies(query, apiKey)
                if (response.isSuccessful) {
                    // Assuming the correct property name is "Search" from MovieSearchResponse
                    response.body()?.movies
                } else {
                    // Log error with more information about the API response
                    Log.e("MovieRepository", "API Error: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                // Log network errors
                Log.e("MovieRepository", "Network Error: ${e.message}", e)
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
                    // Return the movie details if the response is successful
                    response.body()
                } else {
                    // Log API error with more details
                    Log.e("MovieRepository", "API Error: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                // Log network exceptions with detailed message
                Log.e("MovieRepository", "Network Error: ${e.message}", e)
                null
            }
        }
    }
}
