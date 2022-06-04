package com.myfitness.api.services

import com.myfitness.api.models.FitnessResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface FitnessAPI {


    @GET("api/")
    suspend fun getRandomUser(
        @Query("page",encoded=true) page:Int, @Query("results",encoded=true) results:Int
    ):Response<FitnessResponse>
}