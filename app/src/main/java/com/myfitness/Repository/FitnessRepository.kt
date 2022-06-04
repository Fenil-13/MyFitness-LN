package com.myfitness.Repository

import androidx.lifecycle.LiveData
import com.myfitness.api.FitnessClient
import com.myfitness.api.models.FitnessResponse
import com.myfitness.api.models.Result
import retrofit2.Response

class FitnessRepository {
    var page=0;
    var result=50;
    suspend fun getUserData():Response<FitnessResponse>{
            page++
        val response=FitnessClient.publicApi.getRandomUser(page,result);
        return response;
    }
}