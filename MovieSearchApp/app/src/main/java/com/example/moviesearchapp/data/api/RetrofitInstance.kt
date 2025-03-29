package com.example.moviesearchapp.data.api

import com.example.moviesearchapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: OmdbApiService by lazy {
        retrofit.create(OmdbApiService::class.java)
    }
}
