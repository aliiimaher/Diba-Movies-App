package com.example.dibamovies.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dibamovies.databinding.FragmentSearchBinding
import com.example.dibamovies.presentation.ui.detail.DetailActivity
import com.example.dibamovies.utils.NetworkUtils

class SearchFragment : Fragment() {
    //region properties
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchMoviesAdapter
    //endregion

    //region lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, SearchViewModelFactory())[SearchViewModel::class.java]
        adapter = SearchMoviesAdapter { movie ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("movie_id", movie.id)
            startActivity(intent)
        }

        binding.searchedMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchedMoviesRecyclerView.adapter = adapter

        binding.editableTextInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchMovie(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.searchedMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }
        NetworkUtils.registerNetworkCallback(requireContext(), binding.root)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion
}
