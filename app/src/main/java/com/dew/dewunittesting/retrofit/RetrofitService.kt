package com.dew.dewunittesting.retrofit

import com.dew.dewunittesting.Movie
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("movielist")
    suspend fun getAllMovies(): Response<List<Movie>>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                val retrofit = Retrofit.Builder()
                    .client(
                        OkHttpClient.Builder().addInterceptor(MockInterceptor())
                            .addInterceptor(loggingInterceptor).build()
                    )
                    .baseUrl("https://yoururl.com/")
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}
