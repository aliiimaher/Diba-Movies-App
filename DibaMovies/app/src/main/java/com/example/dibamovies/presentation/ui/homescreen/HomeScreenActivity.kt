package com.example.dibamovies.presentation.ui.homescreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dibamovies.databinding.ActivityHomeScreenBinding
import com.example.dibamovies.presentation.ui.homescreen.adapters.GenresAdapter
import com.example.dibamovies.presentation.ui.homescreen.adapters.LastMoviesAdapter
import com.example.dibamovies.presentation.ui.homescreen.adapters.TopMoviesAdapter
import com.example.dibamovies.shared_component.UiUtils
import com.google.android.material.tabs.TabLayoutMediator

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
        configViewModel()
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
        viewModel.getTopMovies()
        viewModel.getAllGenre()
        viewModel.getLastMovies()
    }

    private fun configViewModel() {
        viewModel.topMovies.observe(this) { topMovies ->
            val adapter = TopMoviesAdapter(topMovies)
            binding.viewPagerSlider.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.viewPagerSlider) { tab, position ->
                tab.text = "Tab ${position + 1}"
            }.attach()
        }
        viewModel.genres.observe(this) { genres ->
            val adapter = GenresAdapter(genres)
            binding.genresRecyclerView.adapter = adapter
            binding.genresRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.lastMovies.observe(this) { lastMovies ->
            val adapter = LastMoviesAdapter(lastMovies)
            binding.moviesRecyclerView.adapter = adapter
            binding.moviesRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }
    //endregion
}