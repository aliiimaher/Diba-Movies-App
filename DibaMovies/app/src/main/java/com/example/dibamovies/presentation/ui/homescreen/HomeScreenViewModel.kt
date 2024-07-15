package com.example.dibamovies.presentation.ui.homescreen

import android.util.Log
import androidx.lifecycle.*
import com.example.dibamovies.domain.data.model.movie.GenresResponse
import com.example.dibamovies.domain.data.repository.GenreRepository
import com.example.dibamovies.shared_component.api.API
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val genresRepository: GenreRepository) : ViewModel() {
    //region Properties
    private val _genres = MutableLiveData<List<GenresResponse.Data>>()
    val genres: LiveData<List<GenresResponse.Data>> get() = _genres
    // endregion

    //region Methods
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
    //endregion
}

class HomeScreenModule {
    companion object {
        val watchRepository: GenreRepository by lazy {
            GenreRepository(API.baseUserService)
        }
    }
}

class HomeScreenViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(HomeScreenModule.watchRepository) as T
        }
        throw java.lang.IllegalArgumentException("wrong dependency")
    }
}