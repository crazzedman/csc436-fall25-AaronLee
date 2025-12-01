package com.zybooks.discountdealapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.data.DealRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NearbyDealsViewModel : ViewModel() {

    private val repository = DealRepository()

    var userLatitude by mutableDoubleStateOf(35.2828)
        private set
    var userLongitude by mutableDoubleStateOf(-120.6596)
        private set

    var nearbyDeals by mutableStateOf<List<Deal>>(emptyList())
        private set

    var scrollIndex by mutableIntStateOf(0)
        private set
    var scrollOffset by mutableIntStateOf(0)
        private set

    var isLoading by mutableStateOf(true)
        private set

    private var locationUpdateJob: Job? = null

    init {
        loadNearbyDeals()
    }

    private fun loadNearbyDeals(maxDistanceMiles: Double = 10.0) {
        isLoading = true
        viewModelScope.launch {
            nearbyDeals = try {
                repository.getNearbyDeals(
                    userLat = userLatitude,
                    userLng = userLongitude,
                    maxDistanceMiles = maxDistanceMiles
                )
            } catch (_: Exception) {
                emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    fun setUserLocation(lat: Double, lng: Double) {
        // Update values
        userLatitude = lat
        userLongitude = lng

        // Cancel any previous scheduled update
        locationUpdateJob?.cancel()

        // Schedule a new update after a short delay
        locationUpdateJob = viewModelScope.launch {
            delay(500L) // avoid spamming repository
            loadNearbyDeals()
        }
    }

    fun saveScrollState(index: Int, offset: Int) {
        scrollIndex = index
        scrollOffset = offset
    }
}

