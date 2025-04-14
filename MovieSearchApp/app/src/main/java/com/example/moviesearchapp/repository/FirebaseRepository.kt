package com.example.moviesearchapp.repository

import com.example.moviesearchapp.data.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirebaseRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val movieCollection = firestore.collection("movies")

    // 🔐 AUTH
    fun registerUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    task.exception?.message?.let { onComplete(false, it) }
                }
            }
    }

    fun loginUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    task.exception?.message?.let { onComplete(false, it) }
                }
            }
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    // 📁 FIRESTORE: Add or update movie
    suspend fun addOrUpdateMovie(movie: Movie) {
        val userId = getCurrentUserId() ?: return
        val newMovie = movie.copy(userId = userId)
        try {
            if (movie.documentId.isEmpty()) {
                movieCollection.add(newMovie).await()
            } else {
                movieCollection.document(movie.documentId).set(newMovie).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Log the error to understand what went wrong
        }
    }

    // 📥 Get all movies for the current user
    suspend fun getMovies(): List<Movie> {
        val userId = getCurrentUserId() ?: return emptyList()  // Make sure the user is authenticated
        return try {
            val snapshot = movieCollection
                .whereEqualTo("userId", userId) // Filter by the logged-in user
                .orderBy("title", Query.Direction.ASCENDING)  // Order by title or other field
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->
                document.toObject(Movie::class.java)?.apply {
                    documentId = document.id  // Get the documentId for updates and deletion
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Log the error to understand what went wrong
            emptyList()  // Return an empty list in case of failure
        }
    }

    // ❌ Delete a movie
    suspend fun deleteMovie(documentId: String) {
        try {
            movieCollection.document(documentId).delete().await()  // Delete the movie by documentId
        } catch (e: Exception) {
            e.printStackTrace()  // Log the error to understand what went wrong
        }
    }
}
