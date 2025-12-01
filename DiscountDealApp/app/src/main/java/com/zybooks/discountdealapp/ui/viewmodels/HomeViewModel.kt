package com.zybooks.discountdealapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.data.DealRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = DealRepository()

    var isLoading by mutableStateOf(false)
        private set

    var totalDealsCount by mutableIntStateOf(0)
        private set

    var featuredDeals by mutableStateOf<List<Deal>>(emptyList())
        private set

    var categories by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        loadHomeData()
        loadFeaturedDeals()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {
                isLoading = true

                val allDeals = repository.getAllDeals()

                totalDealsCount = allDeals.size
                categories = allDeals.map { it.category }.distinct()

            } catch (_: Exception) {
                totalDealsCount = 0
                categories = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
    private fun loadFeaturedDeals() {
        viewModelScope.launch {
            isLoading = true
            featuredDeals = try {
                repository.getFeaturedDeals()
            } catch (_: Exception) {
                emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}
