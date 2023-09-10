package com.dew.dewunittesting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dew.dewunittesting.retrofit.Resources
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: IMovieRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        viewModelScope.launch {
            loading.postValue(true)
            val response = movieRepository.getAllMovies()
            loading.postValue(false)
            when (response) {
                is Resources.Success -> {
                    movieList.postValue(response.data ?: arrayListOf())
                }
                is Resources.Error -> {
                    errorMessage.postValue("Error")
                }
            }
        }
    }
}