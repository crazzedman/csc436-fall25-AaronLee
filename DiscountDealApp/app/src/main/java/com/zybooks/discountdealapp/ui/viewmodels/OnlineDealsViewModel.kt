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

class OnlineDealsViewModel : ViewModel() {

    private val repository = DealRepository()

    var allOnlineDeals by mutableStateOf<List<Deal>>(emptyList())
        private set

    var categories by mutableStateOf<List<String>>(emptyList())
        private set

    var selectedCategory by mutableStateOf("All")
        private set

    val filteredDeals: List<Deal>
        get() = if (selectedCategory == "All") {
            allOnlineDeals
        } else {
            allOnlineDeals.filter { it.category == selectedCategory }
        }

    init {
        viewModelScope.launch {
            try {
                val deals = repository.getOnlineDeals()
                allOnlineDeals = deals
                categories = listOf("All") + deals
                    .map { it.category }
                    .distinct()
                    .sorted()
            } catch (_: Exception) {
                allOnlineDeals = emptyList()
                categories = listOf("All")
            }
        }
    }

    fun filterByCategory(category: String) {
        selectedCategory = category
    }

    var scrollIndex by mutableIntStateOf(0)
        private set

    var scrollOffset by mutableIntStateOf(0)
        private set

    fun saveScrollState(index: Int, offset: Int) {
        scrollIndex = index
        scrollOffset = offset
    }
}
