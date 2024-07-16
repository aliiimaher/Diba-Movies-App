package com.example.dibamovies.presentation.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.domain.data.repository.FavoriteMoviesRepository

class FavoriteViewModel(private val favoriteMoviesRepository: FavoriteMoviesRepository) :
    ViewModel() {
    //region Properties
    private val _favorites = MutableLiveData<List<MoviesResponse.Data>>()
    val favorites: LiveData<List<MoviesResponse.Data>> get() = _favorites
    //endregion

    //region Methods
    fun getFavoriteMovies() {
        _favorites.postValue(favoriteMoviesRepository.getFavorites())
    }
    //endregion
}

class FavoriteModule(private val context: Context) {
    companion object {
        private lateinit var instance: FavoriteModule

        fun initialize(context: Context) {
            instance = FavoriteModule(context)
        }

        val watchFavoriteRepository: FavoriteMoviesRepository by lazy {
            FavoriteMoviesRepository(instance.context)
        }
    }
}

class FavoriteViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(FavoriteModule.watchFavoriteRepository) as T
        }
        throw java.lang.IllegalArgumentException("wrong dependency")
    }
}

