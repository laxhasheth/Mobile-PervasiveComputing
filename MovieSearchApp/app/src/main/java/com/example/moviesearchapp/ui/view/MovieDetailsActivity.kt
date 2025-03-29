package com.example.moviesearchapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearchapp.databinding.ActivityMovieDetailsBinding
import com.example.moviesearchapp.ui.viewmodel.MovieViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    // Get the ViewModel instance using the correct viewModels delegate
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of getting the IMDb ID passed in the intent
        val imdbID = intent.getStringExtra("IMDB_ID")

        // You can now use the ViewModel to fetch movie details
        imdbID?.let {
            movieViewModel.getMovieDetails(it) // Assuming you have a method to fetch details by IMDB ID
        }
    }
}
