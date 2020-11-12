package com.scribble.taipeizoo.data.network

import com.scribble.taipeizoo.data.models.Plant

data class Result(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Plant>,
    val sort: String
)

data class PlantResponse(
    val result: Result
)
