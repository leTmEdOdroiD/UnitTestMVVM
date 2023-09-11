package com.dew.dewunittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dew.dewunittesting.retrofit.Resources
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieViewModelTest {

    /*
    * InstantTaskExecutorRule() is used to ensure that LiveData updates happen immediately on the same thread during testing.
   */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private val testDispatcher = StandardTestDispatcher()
    private lateinit var movieViewModel: MovieViewModel

    /*
    * @Mock annotation is used to create a mock object  for the MovieRepository class, which is a dependency of the MovieViewModel
    */
    @Mock
    lateinit var movieRepository: MovieRepository


    @Before
    fun setUp() {
        /*
        * MockitoAnnotations.openMocks(this) initializes the annotated mocks
        * and assigns them to the corresponding fields.
        * */
        MockitoAnnotations.openMocks(this)

        /*
        * Dispatchers.setMain(testDispatcher) sets the test dispatcher as the main dispatcher for coroutines.
        * */
        Dispatchers.setMain(testDispatcher)


        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getAllMovieTest() {
        runTest {
            `when`(movieRepository.getAllMovies())
                .thenReturn(Resources.Success(listOf(Movie("n", "c", "i"))))

            movieViewModel.getAllMovies()

            /*
            * The testDispatcher.scheduler.advanceUntilIdle() is used to advance the dispatcher's time to ensure that all coroutines are executed.
            * */
            testDispatcher.scheduler.advanceUntilIdle()

            val result = movieViewModel.movieList.getOrAwaitValue()
            assertEquals(listOf<Movie>(Movie("n", "c", "i")), result)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.shutdown()
    }

}