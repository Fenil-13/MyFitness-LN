package com.myfitness.api

import com.myfitness.api.services.FitnessAPI
import com.myfitness.utils.Utils.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FitnessClient {
    val retrofitBuilder=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val publicApi=retrofitBuilder.build().create(FitnessAPI::class.java)
}