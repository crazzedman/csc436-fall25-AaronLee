package com.zybooks.discountdealapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.data.DealRepository
import kotlinx.coroutines.launch

class DealDetailsViewModel(
    private val state: SavedStateHandle
) : ViewModel() {

    private val repository = DealRepository()

    var selectedDeal: Deal?
        get() = state.get<Deal>("selectedDeal")
        private set(value) {
            state["selectedDeal"] = value
        }

    init {
        val dealId: Int? = state["dealId"]
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
