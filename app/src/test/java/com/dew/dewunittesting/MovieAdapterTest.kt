package com.dew.dewunittesting

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MovieAdapterTest {

    private lateinit var movieAdapter: MovieAdapter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieAdapter = Mockito.spy(MovieAdapter::class.java)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getItemCount_ReturnsCorrectItemCount() {
        Mockito.doNothing().`when`(movieAdapter).notifyDataSetChanged()
        val movie = Mockito.mock(Movie::class.java)
        val movies = arrayListOf<Movie>(movie, movie)
        movieAdapter.setMovies(movies)
        assertEquals(movieAdapter.itemCount, movies.size)
    }

}