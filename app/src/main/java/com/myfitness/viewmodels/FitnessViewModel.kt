package com.myfitness.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myfitness.Repository.FitnessRepository
import com.myfitness.api.models.FitnessResponse
import com.myfitness.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class FitnessViewModel(application: Application, val fitnessRepository: FitnessRepository) : ViewModel() {
    val allRandomUser:MutableLiveData<Resource<FitnessResponse>> = MutableLiveData()
    init {
        viewModelScope.launch {
            getRandomUser()
        }
    }

    suspend fun getRandomUser(){
        allRandomUser.postValue(Resource.Loading())
        try {
            val response = fitnessRepository.getUserData()
            allRandomUser.postValue(handleAllRestauntResponse(response))
        }catch (t: Throwable) {
            when (t) {
                is IOException -> allRandomUser.postValue(Resource.Error("Network Failure"))
                else -> allRandomUser.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleAllRestauntResponse(response: Response<FitnessResponse>):
            Resource<FitnessResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}