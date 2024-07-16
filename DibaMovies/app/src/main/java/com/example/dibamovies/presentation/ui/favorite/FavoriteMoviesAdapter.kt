package com.example.dibamovies.presentation.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dibamovies.databinding.ItemMovieBinding
import com.example.dibamovies.domain.data.model.movie.MoviesResponse

class FavoriteMoviesAdapter(private val movies: List<MoviesResponse.Data>) :
    RecyclerView.Adapter<FavoriteMoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesResponse.Data) {
            binding.movieName.text = movie.title
            if (movie.poster.isNotEmpty()) {
                Glide.with(binding.moviePic.context)
                    .load(movie.poster)
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .into(binding.moviePic)
            }
        }
    }
}