package com.zybooks.discountdealapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*

class DealRepository {

    private val api = RetrofitInstance.api


    suspend fun getOnlineDeals(): List<Deal> = withContext(Dispatchers.IO) {
        api.getAllDeals().filter { it.isOnline }
    }

    suspend fun getNearbyDeals(
        userLat: Double = 35.2828,
        userLng: Double = -120.6596,
        maxDistanceMiles: Double = 10.0
    ): List<Deal> = withContext(Dispatchers.IO) {
        api.getAllDeals().filter { !it.isOnline &&
                calculateDistanceMiles(userLat, userLng, it.latitude, it.longitude) <= maxDistanceMiles
        }
    }

    suspend fun getFeaturedDeals(limit: Int = 5): List<Deal> = withContext(Dispatchers.IO) {
        api.getAllDeals().sortedByDescending { it.discountAmount }.take(limit)
    }

    suspend fun getDealById(id: Int): Deal? = withContext(Dispatchers.IO) {
        api.getDealById(id)
    }


    suspend fun getAllDeals(): List<Deal> = withContext(Dispatchers.IO) {
        api.getAllDeals()
    }


    private fun calculateDistanceMiles(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val radius = 3958.8
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) +
                cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return radius * c
    }
}
