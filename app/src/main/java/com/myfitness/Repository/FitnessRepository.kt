package com.myfitness.Repository

import androidx.lifecycle.LiveData
import com.myfitness.api.FitnessClient
import com.myfitness.api.models.FitnessResponse
import com.myfitness.api.models.Result
import retrofit2.Response

class FitnessRepository {
    suspend fun getUserData():Response<FitnessResponse>{
        val response=FitnessClient.publicApi.getRandomUser();
        return response;
    }
}