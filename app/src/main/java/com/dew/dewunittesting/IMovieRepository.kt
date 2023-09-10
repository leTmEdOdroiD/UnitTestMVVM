package com.dew.dewunittesting

import com.dew.dewunittesting.retrofit.Resources

interface IMovieRepository {
   suspend fun getAllMovies(): Resources<List<Movie>>
}