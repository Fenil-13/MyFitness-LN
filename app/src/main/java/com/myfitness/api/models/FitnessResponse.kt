package com.myfitness.api.models

data class FitnessResponse(
    val info: Info,
    val results: List<Result>
)