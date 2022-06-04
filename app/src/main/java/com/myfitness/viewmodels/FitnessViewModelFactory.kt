package com.myfitness.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myfitness.Repository.FitnessRepository

class FitnessViewModelFactory(
    private val app: Application,
    private val fitnessRepository: FitnessRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FitnessViewModel(app, fitnessRepository) as T
    }

}