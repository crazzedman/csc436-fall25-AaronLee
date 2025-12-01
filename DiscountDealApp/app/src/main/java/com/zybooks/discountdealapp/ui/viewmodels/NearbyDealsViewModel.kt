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
import kotlinx.coroutines.launch

class NearbyDealsViewModel : ViewModel() {

    private val repository = DealRepository()


    // Loaded deals
    var nearbyDeals by mutableStateOf<List<Deal>>(repository.getNearbyDeals())
        private set

    // Scroll state
    var scrollIndex by mutableIntStateOf(0)
        private set

    var scrollOffset by mutableIntStateOf(0)
        private set

    /** Save LazyColumn scroll state */
    fun saveScrollState(index: Int, offset: Int) {
        scrollIndex = index
        scrollOffset = offset
    }
}
