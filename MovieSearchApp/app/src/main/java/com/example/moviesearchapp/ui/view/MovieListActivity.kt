package com.example.moviesearchapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearchapp.data.model.Movie
import com.example.moviesearchapp.databinding.ActivityMovieListBinding
import com.example.moviesearchapp.ui.adapter.FavoriteMovieAdapter
import com.example.moviesearchapp.ui.viewmodel.FirebaseViewModel

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()
    private val movieList = mutableListOf<Movie>()
    private lateinit var adapter: FavoriteMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        // Show loading while movies are fetched
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
        binding.rvFavoriteMovies.visibility = View.GONE

        // Load Firestore movies
        firebaseViewModel.loadMovies()

        // Add Movie button click
        binding.fabAddMovie.setOnClickListener {
            val intent = Intent(this, AddEditMovieActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoriteMovieAdapter(movieList) { movie ->
            val intent = Intent(this, MovieDetailsActivity::class.java)
            intent.putExtra("IMDB_ID", movie.imdbID)
            startActivity(intent)
        }
        binding.rvFavoriteMovies.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteMovies.adapter = adapter
    }

    private fun observeViewModel() {
        firebaseViewModel.movies.observe(this) { movies ->
            binding.progressBar.visibility = View.GONE

            if (movies.isNullOrEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvFavoriteMovies.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvFavoriteMovies.visibility = View.VISIBLE

                movieList.clear()
                movieList.addAll(movies)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
