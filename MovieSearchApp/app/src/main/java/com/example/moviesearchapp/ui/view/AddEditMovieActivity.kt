package com.example.moviesearchapp.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearchapp.data.model.Movie
import com.example.moviesearchapp.databinding.ActivityAddEditMovieBinding
import com.example.moviesearchapp.ui.viewmodel.FirebaseViewModel

class AddEditMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditMovieBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveMovie.setOnClickListener {
            val title = binding.etMovieTitle.text.toString()
            val poster = binding.etMoviePoster.text.toString()
            val imdbRating = binding.etImdbRating.text.toString()
            val language = binding.etLanguage.text.toString()
            val metascore = binding.etMetascore.text.toString()
            val plot = binding.etPlot.text.toString()
            val production = binding.etProduction.text.toString()
            val rated = binding.etRated.text.toString()
            val released = binding.etReleased.text.toString()
            val runtime = binding.etRuntime.text.toString()
            val writer = binding.etWriter.text.toString()
            val year = binding.etYear.text.toString()

            if (title.isNotEmpty() && poster.isNotEmpty()) {
                val movie = Movie(
                    imdbID = "",
                    title = title,
                    year = year,
                    rated = rated,
                    released = released,
                    runtime = runtime,
                    genre = "",
                    director = "",
                    writer = writer,
                    actors = "",
                    plot = plot,
                    language = language,
                    country = "",
                    awards = "",
                    poster = poster,
                    metascore = metascore,
                    imdbRating = imdbRating,
                    production = production,
                    documentId = ""
                )

                val movieId = intent.getStringExtra("MOVIE_ID")


                firebaseViewModel.addOrUpdateMovie(movie)
                Toast.makeText(this, "Movie saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill at least Title and Poster", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
