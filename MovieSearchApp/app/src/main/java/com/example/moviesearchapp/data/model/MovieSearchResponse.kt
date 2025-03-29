package com.example.moviesearchapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search") val movies: List<Movie>?, // This field holds the list of movies
    @SerializedName("totalResults") val totalResults: String?, // Total number of results found
    @SerializedName("Response") val response: String? // Whether the request was successful (e.g., "True" or "False")
)
