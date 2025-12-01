package com.zybooks.discountdealapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    var darkModeEnabled by mutableStateOf(false)
    var locationPermissionGranted by mutableStateOf(false)
    var appVersion by mutableStateOf("1.0.0")

    fun setDarkMode(enabled: Boolean) {
        darkModeEnabled = enabled
    }

    fun setLocationPermission(granted: Boolean) {
        locationPermissionGranted = granted
    }


}