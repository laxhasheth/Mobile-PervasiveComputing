package com.example.moviesearchapp.data.api

import com.example.moviesearchapp.data.model.MovieSearchResponse
import com.example.moviesearchapp.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {

    // API call to search movies
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String
    ): Response<MovieSearchResponse>

    // API call to get movie details by IMDb ID
    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String
    ): Response<Movie>
}
