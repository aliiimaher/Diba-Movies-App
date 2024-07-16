package com.example.dibamovies.presentation.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.example.dibamovies.domain.data.model.movie.MoviesResponse
import com.example.dibamovies.domain.data.repository.SearchRepository
import com.example.dibamovies.shared_component.api.API
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    //region Properties
    private val _searchedMovies = MutableLiveData<List<MoviesResponse.Data>>()
    val searchedMovies: LiveData<List<MoviesResponse.Data>> get() = _searchedMovies
    private val searchJob: Job? = null
    //endregion

    //region Methods
    fun searchMovie(query: String) {
        searchJob?.cancel()
        viewModelScope.launch {
            val response = searchRepository.searchMovie(query)
            if (response.isSuccess) {
                delay(300)
                response.onSuccess {
                    _searchedMovies.postValue(it.data)
                }.onFailure {
                    Log.e("2323", "Make sure you are connected to network!!!")
                }
            }
        }
    }
    //endregion
}

class SearchModule {
    companion object {
        val watchRepository: SearchRepository by lazy {
            SearchRepository(API.baseUserService)
        }
    }
}

class SearchViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                SearchModule.watchRepository
            ) as T
        }
        throw java.lang.IllegalArgumentException("wrong dependency")
    }
}