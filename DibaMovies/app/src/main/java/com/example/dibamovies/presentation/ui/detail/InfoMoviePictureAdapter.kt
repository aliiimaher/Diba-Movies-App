package com.example.dibamovies.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dibamovies.databinding.ItemInfoMoviePicBinding

class InfoMoviePictureAdapter(private val pictures: List<String>) :
    RecyclerView.Adapter<InfoMoviePictureAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemInfoMoviePicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: String) {
            if (item.isNotEmpty()) {
                Glide.with(binding.picture.context)
                    .load(item)
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .into(binding.picture)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInfoMoviePicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = pictures.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(pictures[position])
    }
}