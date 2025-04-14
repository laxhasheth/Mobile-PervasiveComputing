package com.example.moviesearchapp.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearchapp.databinding.ActivityMovieDetailsBinding
import com.example.moviesearchapp.ui.viewmodel.MovieViewModel

class MovieDetailsActivity : AppCompatActivity() {

    // ViewBinding instance for accessing the views in the layout
    private lateinit var binding: ActivityMovieDetailsBinding

    // Get the ViewModel instance using the correct viewModels delegate
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the IMDb ID passed in the intent
        val imdbID = intent.getStringExtra("IMDB_ID")

        // Observe movie details LiveData
        movieViewModel.movieDetails.observe(this) { movie ->
            // Update the UI with movie details once available
            movie?.let {
                // Set movie details to UI components using ViewBinding
                binding.tvMovieTitle.text = it.title
                binding.tvMovieDescription.text = it.plot
                // Add more fields as needed, like genre, rating, etc.
            } ?: run {
                // Handle the case when no movie details are found
                Toast.makeText(this, "Movie details not found", Toast.LENGTH_SHORT).show()
            }
        }

        // If IMDb ID is present, fetch the movie details
        imdbID?.let {
            movieViewModel.getMovieDetails(it)  // Fetch details by IMDb ID
        }
    }
}
