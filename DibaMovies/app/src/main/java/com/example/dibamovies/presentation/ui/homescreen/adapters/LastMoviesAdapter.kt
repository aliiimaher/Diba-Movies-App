package com.example.dibamovies.presentation.ui.homescreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dibamovies.databinding.ItemMovieBinding
import com.example.dibamovies.domain.data.model.movie.MoviesResponse

class LastMoviesAdapter(private val lastMovies: List<MoviesResponse.Data>) :
    RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: MoviesResponse.Data) {
            binding.movieName.text = item.title
            if (item.poster.isNotEmpty()) {
                Glide.with(binding.moviePic.context)
                    .load(item.poster)
                    .into(binding.moviePic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = lastMovies.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData((lastMovies[position]))
    }
}