package com.scribble.taipeizoo.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    suspend fun getPlants(@Query("q") query: String,
                          @Query("limit") limit: Int,
                          @Query("offset") offset: Int)
            : Response<PlantResponse?>
}