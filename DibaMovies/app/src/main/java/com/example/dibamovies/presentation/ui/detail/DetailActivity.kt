package com.example.dibamovies.presentation.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dibamovies.R
import com.example.dibamovies.databinding.ActivityDetailBinding
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.shared_component.UiUtils
import com.example.dibamovies.utils.FavoriteMoviesManager

class DetailActivity : AppCompatActivity() {
    //region Properties
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        initialBinding()
        initialViewModel(fetchId())
        configViewModel()
        configReturnBtn()
    }
    //endregion

    //region Methods
    private fun initialBinding() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun fetchId(): Int {
        return intent.getIntExtra("movie_id", 1)
    }

    private fun initialViewModel(movie_id: Int) {
        viewModel = ViewModelProvider(this, DetailViewModelFactory())[DetailViewModel::class.java]
        viewModel.getMovieDetail(movie_id)
    }

    private fun configViewModel() {
        viewModel.movie.observe(this) { movie ->
            val adapter = InfoMoviePictureAdapter(movie.images)
            binding.moviePicturesRecycler.adapter = adapter
            binding.moviePicturesRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            val favoriteMovie = MoviesResponse.Data(
                genres = movie.genres,
                id = movie.id,
                images = movie.images,
                poster = movie.poster,
                title = movie.title
            )
            configFavoriteBtn(favoriteMovie)
        }
    }

    private fun configReturnBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun configFavoriteBtn(movie: MoviesResponse.Data) {
        updateFavoriteIcon(movie.id)

        binding.favIcon.setOnClickListener {
            if (FavoriteMoviesManager.isFavorite(this, movie.id)) {
                FavoriteMoviesManager.removeFavorite(this, movie.id)
            } else {
                FavoriteMoviesManager.saveFavorite(this, movie)
            }
            updateFavoriteIcon(movie.id)
        }
    }

    private fun updateFavoriteIcon(movieId: Int) {
        val isFavorite = FavoriteMoviesManager.isFavorite(this, movieId)
        val iconRes =
            if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        binding.favIcon.setImageResource(iconRes)
    }
    //endregion
}