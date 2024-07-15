package com.example.dibamovies.presentation.ui.homescreen

import android.util.Log
import androidx.lifecycle.*
import com.example.dibamovies.domain.data.model.movie.GenresResponse
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.domain.data.repository.GenreRepository
import com.example.dibamovies.domain.data.repository.LastMoviesRepository
import com.example.dibamovies.domain.data.repository.TopMoviesRepository
import com.example.dibamovies.shared_component.api.API
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val topMoviesRepository: TopMoviesRepository,
    private val genresRepository: GenreRepository,
    private val lastMoviesRepository: LastMoviesRepository,
) : ViewModel() {
    //region Properties
    private val _topMovies = MutableLiveData<List<MoviesResponse.Data>>()
    val topMovies: LiveData<List<MoviesResponse.Data>> get() = _topMovies
    private val _genres = MutableLiveData<List<GenresResponse.Data>>()
    val genres: LiveData<List<GenresResponse.Data>> get() = _genres
    private val _lastMovies = MutableLiveData<List<MoviesResponse.Data>>()
    val lastMovies: LiveData<List<MoviesResponse.Data>> get() = _lastMovies
    // endregion

    //region Methods
    fun getTopMovies() {
        viewModelScope.launch {
            val response = topMoviesRepository.getTopMovies()
            if (response.isSuccess) {
                response.onSuccess {
                    _topMovies.postValue(it.data)
                }.onFailure {
                    Log.e("2323", "Make sure you are connected to network!!!")
                }
            }
        }
    }

    fun getAllGenre() {
        viewModelScope.launch {
            val response = genresRepository.getAllGenres()
            if (response.isSuccess) {
                response.onSuccess {
                    _genres.postValue(it.data)
                }.onFailure {
                    Log.e("2323", "Make sure you are connected to network!!!")
                }
            }
        }
    }

    fun getLastMovies() {
        viewModelScope.launch {
            val response = lastMoviesRepository.getLastMovies()
            if (response.isSuccess) {
                response.onSuccess {
                    _lastMovies.postValue(it.data)
                }.onFailure {
                    Log.e("2323", "Make sure you are connected to network!!!")
                }
            }
        }
    }
    //endregion
}

class HomeScreenModule {
    companion object {
        val watchTopMoviesRepository: TopMoviesRepository by lazy {
            TopMoviesRepository(API.baseUserService)
        }
        val watchRepository: GenreRepository by lazy {
            GenreRepository(API.baseUserService)
        }
        val watchLastMoviesRepository: LastMoviesRepository by lazy {
            LastMoviesRepository(API.baseUserService)
        }
    }
}

class HomeScreenViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(
                HomeScreenModule.watchTopMoviesRepository,
                HomeScreenModule.watchRepository,
                HomeScreenModule.watchLastMoviesRepository
            ) as T
        }
        throw java.lang.IllegalArgumentException("wrong dependency")
    }
}