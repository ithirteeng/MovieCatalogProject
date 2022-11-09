package com.example.moviecatalogproject.data.service

import com.example.moviecatalogproject.data.service.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkService {

    private const val BASE_URL = "https://react-midterm.kreosoft.space/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(NetworkConnectionInterceptor())
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val authenticationApiService: AuthenticationApiService by lazy {
        retrofit.create(AuthenticationApiService::class.java)
    }

    val profileApiService: ProfileApiService by lazy {
        retrofit.create(ProfileApiService::class.java)
    }

    val moviesApiService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }

    val reviewApiService: ReviewApiService by lazy {
        retrofit.create(ReviewApiService::class.java)
    }

}