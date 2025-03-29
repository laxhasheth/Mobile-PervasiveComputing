package com.example.moviesearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearchapp.data.model.Movie
import com.example.moviesearchapp.R

class MovieAdapter(private val movieList: List<Movie>, private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val year: TextView = itemView.findViewById(R.id.year)
        private val rating: TextView = itemView.findViewById(R.id.rating)
        private val poster: ImageView = itemView.findViewById(R.id.poster)

        fun bind(movie: Movie) {
            title.text = movie.title
            year.text = movie.year
            rating.text = movie.rated
            Glide.with(poster.context)
                .load(movie.poster)
                .into(poster)

            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}
