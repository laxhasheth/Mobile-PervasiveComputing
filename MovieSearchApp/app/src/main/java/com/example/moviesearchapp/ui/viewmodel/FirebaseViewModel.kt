package com.example.moviesearchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesearchapp.data.model.Movie
import com.example.moviesearchapp.repository.FirebaseRepository
import kotlinx.coroutines.launch

class FirebaseViewModel : ViewModel() {

    private val repository = FirebaseRepository()
    private val TAG = "FirebaseViewModel"

    // 🔐 AUTH
    private val _authResult = MutableLiveData<Pair<Boolean, String?>>()
    val authResult: LiveData<Pair<Boolean, String?>> get() = _authResult

    private val _isLoadingAuth = MutableLiveData<Boolean>()
    val isLoadingAuth: LiveData<Boolean> get() = _isLoadingAuth

    fun register(email: String, password: String) {
        _isLoadingAuth.value = true
        repository.registerUser(email, password) { success, error ->
            Log.d(TAG, "Register result: success=$success, error=$error")
            _authResult.value = Pair(success, error)
            _isLoadingAuth.value = false
        }
    }

    fun login(email: String, password: String) {
        _isLoadingAuth.value = true
        repository.loginUser(email, password) { success, error ->
            Log.d(TAG, "Login result: success=$success, error=$error")
            _authResult.value = Pair(success, error)
            _isLoadingAuth.value = false
        }
    }

    fun logout() {
        repository.logoutUser()
        Log.d(TAG, "User logged out")
    }

    fun getCurrentUserId(): String? = repository.getCurrentUserId()

    fun isUserLoggedIn(): Boolean = getCurrentUserId() != null

    // 🎬 MOVIES
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _isLoadingMovies = MutableLiveData<Boolean>()
    val isLoadingMovies: LiveData<Boolean> get() = _isLoadingMovies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadMovies() {
        _isLoadingMovies.value = true
        viewModelScope.launch {
            try {
                val movieList = repository.getMovies()
                Log.d(TAG, "Loaded movies: ${movieList.size}")
                _movies.postValue(movieList)
            } catch (e: Exception) {
                val msg = "Error loading movies: ${e.message}"
                Log.e(TAG, msg)
                _errorMessage.postValue(msg)
                _movies.postValue(emptyList())
            } finally {
                _isLoadingMovies.postValue(false)
            }
        }
    }

    fun addOrUpdateMovie(movie: Movie) {
        _isLoadingMovies.value = true
        viewModelScope.launch {
            val userId = getCurrentUserId()
            if (userId == null) {
                Log.e(TAG, "Cannot add movie. User not logged in.")
                _errorMessage.postValue("You must be logged in to add a movie.")
                _isLoadingMovies.postValue(false)
                return@launch
            }

            try {
                Log.d(TAG, "Adding movie: ${movie.title} by user: $userId")
                repository.addOrUpdateMovie(movie)
                Log.d(TAG, "Movie added/updated: ${movie.title}")
                loadMovies()
            } catch (e: Exception) {
                val msg = "Error adding/updating movie: ${e.message}"
                Log.e(TAG, msg)
                _errorMessage.postValue(msg)
            } finally {
                _isLoadingMovies.postValue(false)
            }
        }
    }

    fun deleteMovie(documentId: String) {
        _isLoadingMovies.value = true
        viewModelScope.launch {
            try {
                repository.deleteMovie(documentId)
                Log.d(TAG, "Movie deleted with ID: $documentId")
                loadMovies()
            } catch (e: Exception) {
                val msg = "Error deleting movie: ${e.message}"
                Log.e(TAG, msg)
                _errorMessage.postValue(msg)
            } finally {
                _isLoadingMovies.postValue(false)
            }
        }
    }
}
