package com.example.dibamovies.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dibamovies.databinding.ItemMovieBinding
import com.example.dibamovies.domain.data.model.movie.MoviesResponse

class SearchMoviesAdapter(private val onClick: (MoviesResponse.Data) -> Unit) :
    ListAdapter<MoviesResponse.Data, SearchMoviesAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MoviesResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: MoviesResponse.Data,
            newItem: MoviesResponse.Data,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesResponse.Data,
            newItem: MoviesResponse.Data,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: MoviesResponse.Data) {
            binding.movieName.text = item.title
            if (item.poster.isNotEmpty()) {
                Glide.with(binding.moviePic.context)
                    .load(item.poster)
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .into(binding.moviePic)
            }
            binding.root.setOnClickListener {
                onClick(item)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}
