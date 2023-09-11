package com.dew.dewunittesting

import com.dew.dewunittesting.retrofit.Resources
import com.dew.dewunittesting.retrofit.RetrofitService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MovieRepositoryTest {

    @Mock
    lateinit var retrofitService: RetrofitService

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieRepository = MovieRepository(retrofitService)
    }

    @Test
    fun getAllMovies() {
        runTest {
            Mockito.`when`(retrofitService.getAllMovies())
                .thenReturn(Response.success(listOf<Movie>(Movie("n","c","i"))))
            val response = movieRepository.getAllMovies()
            assertEquals(listOf<Movie>(Movie("n","c","i")), (response as Resources.Success).data)
        }
    }
}