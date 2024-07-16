package com.example.dibamovies.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.dibamovies.domain.data.model.movie.SingleMovieResponse
import com.example.dibamovies.domain.data.repository.SingleMovieRepository
import com.example.dibamovies.shared_component.api.API
import kotlinx.coroutines.launch

class DetailViewModel(
    private val singleMovieRepository: SingleMovieRepository,
) : ViewModel() {
    //region Properties
    private val _movie = MutableLiveData<SingleMovieResponse.Data>()
    val movie: LiveData<SingleMovieResponse.Data> get() = _movie
    //endregion

    //region Methods
    fun getMovieDetail(movie_id: Int) {
        viewModelScope.launch {
            val response = singleMovieRepository.getMovieDetail(movie_id)
            if (response.isSuccess) {
                response.onSuccess {
                    _movie.postValue(it.data)
                }.onFailure {
                    Log.e("2323", "Make sure you are connected to network!!!")
                }
            }

        }
    }
    //endregion
}

class DetailModule {
    companion object {
        val watchRepository: SingleMovieRepository by lazy {
            SingleMovieRepository(API.baseUserService)
        }
    }
}

class DetailViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                DetailModule.watchRepository
            ) as T
        }
        throw java.lang.IllegalArgumentException("wrong dependency")
    }
}