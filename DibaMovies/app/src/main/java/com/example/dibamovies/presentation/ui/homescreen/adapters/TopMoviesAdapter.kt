package com.example.dibamovies.presentation.ui.homescreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dibamovies.databinding.ItemSliderMovieBinding
import com.example.dibamovies.domain.data.model.movie.MoviesResponse

class TopMoviesAdapter(private val topMovies: List<MoviesResponse.Data>) :
    RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemSliderMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: MoviesResponse.Data) {
            binding.textViewTitle.text = item.title
            if (item.poster.isNotEmpty()) {
                Glide.with(binding.imageViewTitle.context)
                    .load(item.poster)
                    .into(binding.imageViewTitle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSliderMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = topMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(topMovies[position])
    }
}