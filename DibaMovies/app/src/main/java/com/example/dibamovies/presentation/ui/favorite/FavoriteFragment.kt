package com.example.dibamovies.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dibamovies.databinding.FragmentFavoriteBinding
import com.example.dibamovies.utils.NetworkUtils

class FavoriteFragment : Fragment() {

    //region properties
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    //endregion

    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, FavoriteViewModelFactory()).get(FavoriteViewModel::class.java)
        setupRecyclerView()
        observeViewModel()
        NetworkUtils.registerNetworkCallback(requireContext(), binding.root)
    }
    //endregion

    //region methods
    private fun setupRecyclerView() {
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            binding.favoriteRecyclerView.adapter = FavoriteMoviesAdapter(favorites)
        })
    }
    //endregion
}