package com.dew.dewunittesting

import com.dew.dewunittesting.retrofit.Resources
import com.dew.dewunittesting.retrofit.RetrofitService

class MovieRepository constructor(private val retrofitService: RetrofitService) : IMovieRepository {

    override suspend fun getAllMovies(): Resources<List<Movie>> {
        val response = retrofitService.getAllMovies()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                Resources.Success(responseBody)
            } else {
                Resources.Error(response)
            }
        } else {
            Resources.Error(response)
        }
    }
}