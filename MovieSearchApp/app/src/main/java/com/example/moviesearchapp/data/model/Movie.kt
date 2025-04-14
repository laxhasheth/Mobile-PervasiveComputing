package com.example.moviesearchapp.data.model

data class Movie(
    val imdbID: String = "",
    val title: String = "",
    val year: String = "",
    val rated: String = "",
    val released: String = "",
    val runtime: String = "",
    val genre: String = "",
    val director: String = "",
    val writer: String = "",
    val actors: String = "",
    val plot: String = "",
    val language: String = "",
    val country: String = "",
    val awards: String = "",
    val poster: String = "",
    val metascore: String = "",
    val imdbRating: String = "",
    val production: String = "",
    val userId: String = "", // Assuming userId is needed for each movie entry
    var documentId: String = "" // Firestore document ID (if updating an existing movie)
) {
    val Search:String=""
}
