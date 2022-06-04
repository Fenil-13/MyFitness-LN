package com.myfitness.api.models

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)