package com.zybooks.discountdealapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.discountdealapp.data.DealRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = DealRepository()

    // Example pieces of home data
    var isLoading by mutableStateOf(false)
        private set

    var totalDealsCount by mutableStateOf(0)
        private set

    var categories by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {
                isLoading = true

                val allDeals = repository.getAllDeals()

                // Basic home data
                totalDealsCount = allDeals.size
                categories = allDeals.map { it.category }.distinct()

            } catch (e: Exception) {
                totalDealsCount = 0
                categories = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
}
