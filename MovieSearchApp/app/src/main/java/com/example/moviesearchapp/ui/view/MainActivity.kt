package com.example.moviesearchapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearchapp.data.api.RetrofitInstance
import com.example.moviesearchapp.databinding.ActivityMainBinding
import com.example.moviesearchapp.ui.adapter.MovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Search button listener
        binding.searchButton.setOnClickListener {
            val query = binding.searchField.text.toString()
            if (query.isNotEmpty()) {
                // Start a coroutine to call the suspend function
                searchMovies(query)
            }
        }
    }

    // Function to search movies based on query
    private fun searchMovies(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitInstance.apiService.searchMovies(query, "f7c44cdb")
                if (response.isSuccessful) {
                    val movies = response.body()?.movies
                    if (movies != null) {
                        // Set up the adapter with movies
                        binding.recyclerView.adapter = MovieAdapter(movies) { movie ->
                            val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                            intent.putExtra("IMDB_ID", movie.imdbID)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "No movies found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
