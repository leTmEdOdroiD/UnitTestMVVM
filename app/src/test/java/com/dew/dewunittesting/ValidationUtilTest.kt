package com.dew.dewunittesting

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationUtilTest {

    @Test
    fun validateMovieTest(){
        val movie = Movie("n","c","url")
        val result = ValidationUtil.validateMovie(movie)
        assertEquals(true,result)
    }

    @Test
    fun validateMovieNameEmptyTest(){
        val movie = Movie("","c","url")
        val result = ValidationUtil.validateMovie(movie)
        assertEquals(false,result)
    }

}