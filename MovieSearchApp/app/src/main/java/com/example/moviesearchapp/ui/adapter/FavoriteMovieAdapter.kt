package com.example.moviesearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearchapp.R
import com.example.moviesearchapp.data.model.Movie
import com.squareup.picasso.Picasso

class FavoriteMovieAdapter(
    private val movieList: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<FavoriteMovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_favorite, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val moviePoster: ImageView = itemView.findViewById(R.id.ivMoviePoster)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            Picasso.get().load(movie.poster).into(moviePoster)

            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}
