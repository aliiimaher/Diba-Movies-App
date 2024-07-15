package com.example.dibamovies.presentation.ui.homescreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dibamovies.databinding.ActivityHomeScreenBinding
import com.example.dibamovies.presentation.ui.homescreen.adapters.GenresAdapter
import com.example.dibamovies.shared_component.UiUtils

class HomeScreenActivity : AppCompatActivity() {

    //region properties
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var viewModel: HomeScreenViewModel
    //endregion

    //region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        initialBinding()
        initialViewModel()
    }
    //endregion

    //region methods
    private fun initialBinding() {
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initialViewModel() {
        viewModel =
            ViewModelProvider(this, HomeScreenViewModelFactory())[HomeScreenViewModel::class.java]
        viewModel.getAllGenre()
    }

    private fun configViewModel() {
        viewModel.genres.observe(this) { genres ->
            val adapter = GenresAdapter(genres)
            binding.genresRecyclerView.adapter = adapter
            binding.genresRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    //endregion
}