package com.example.dibamovies.presentation.ui.homescreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dibamovies.databinding.ItemGenreBinding
import com.example.dibamovies.domain.data.model.movie.GenresResponse

class GenresAdapter(private val genres: List<GenresResponse.Data>) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: GenresResponse.Data) {
            binding.genreItemText.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = genres.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(genres[position])
    }
}

