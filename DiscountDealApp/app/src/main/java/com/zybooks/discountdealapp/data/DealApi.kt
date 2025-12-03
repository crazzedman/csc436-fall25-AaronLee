package com.zybooks.discountdealapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DealApi {
    @GET("deals")
    suspend fun getAllDeals(): List<Deal>

    @GET("deals/{id}")
    suspend fun getDealById(@Path("id") id: Int): Deal
}
