package com.example.dibamovies.presentation.ui.homescreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dibamovies.databinding.FragmentHomeBinding
import com.example.dibamovies.domain.data.Constants
import com.example.dibamovies.presentation.ui.detail.DetailActivity
import com.example.dibamovies.presentation.ui.homescreen.adapters.GenresAdapter
import com.example.dibamovies.presentation.ui.homescreen.adapters.LastMoviesAdapter
import com.example.dibamovies.presentation.ui.homescreen.adapters.TopMoviesAdapter
import com.example.dibamovies.shared_component.OnMovieClickListener
import com.example.dibamovies.utils.NetworkUtils
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(), OnMovieClickListener {
    //region Properties
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeScreenViewModel
    private var currentPage = 0
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnable: Runnable
    //endregion

    //region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NetworkUtils.registerNetworkCallback(requireContext(), binding.root)
        viewModel =
            ViewModelProvider(this, HomeScreenViewModelFactory())[HomeScreenViewModel::class.java]
        viewModel.getTopMovies()
        viewModel.getAllGenre()
        viewModel.getLastMovies()
        configViewModel()
        setupAutoScroll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        stopAutoScroll()
    }
    //endregion

    //region Methods
    private fun configViewModel() {
        viewModel.topMovies.observe(viewLifecycleOwner) { topMovies ->
            val limitedTopMovies = if (topMovies.size > 5) topMovies.subList(0, 5) else topMovies
            val adapter = TopMoviesAdapter(limitedTopMovies)
            binding.viewPagerSlider.adapter = adapter

            TabLayoutMediator(binding.tabLayout, binding.viewPagerSlider) { tab, position ->
                tab.text = "Tab ${position + 1}"
            }.attach()
        }
        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            val adapter = GenresAdapter(genres)
            binding.genresRecyclerView.adapter = adapter
            binding.genresRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.lastMovies.observe(viewLifecycleOwner) { lastMovies ->
            val adapter = LastMoviesAdapter(lastMovies, this)
            binding.moviesRecyclerView.adapter = adapter
            binding.moviesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onMovieClick(movieId: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("movie_id", movieId)
        }
        startActivity(intent)
    }


    private fun setupAutoScroll() {
        autoScrollRunnable = Runnable {
            binding.viewPagerSlider.currentItem = currentPage + 1
        }
        autoScrollHandler.postDelayed(autoScrollRunnable, Constants.AUTO_SCROLL_DELAY)
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }
    //endregion
}