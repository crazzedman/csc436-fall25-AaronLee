package com.zybooks.discountdealapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.data.DealRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DealDetailsViewModel(
    private val state: SavedStateHandle
) : ViewModel() {

    private val repository = DealRepository()

    // store only the ID
    private val dealId: Int? = state["dealId"]

    // hold the loaded deal in Compose state
    var selectedDeal by mutableStateOf<Deal?>(null)
        private set

    init {
        if (dealId != null) {
            loadDeal(dealId)
        }
    }

    private fun loadDeal(id: Int) {
        viewModelScope.launch {
            selectedDeal = try {
                repository.getDealById(id)
            } catch (_: Exception) {
                null
            }
        }
    }
}
