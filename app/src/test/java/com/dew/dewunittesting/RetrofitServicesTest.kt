package com.dew.dewunittesting

import com.dew.dewunittesting.retrofit.RetrofitService
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RetrofitServicesTest {

    private lateinit var retrofitService: RetrofitService
    private lateinit var gson: Gson
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        mockWebServer = MockWebServer()
        gson = Gson()
        retrofitService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RetrofitService::class.java)
    }

    @Test
    fun `get all movie api test`(){
        runTest {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("[]"))

            val response = retrofitService.getAllMovies()
            val request = mockWebServer.takeRequest()
            assertEquals("/movielist",request.path)
            assertEquals(true,response.body()?.isEmpty()==true)
        }
    }

    @After
    fun teardown(){
        mockWebServer.shutdown()
    }

}