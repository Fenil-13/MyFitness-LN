package com.myfitness.api.services

import com.myfitness.api.models.FitnessResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface FitnessAPI {


    @GET("?page=1&results=10")
    suspend fun getRandomUser(
    ):Response<FitnessResponse>
}