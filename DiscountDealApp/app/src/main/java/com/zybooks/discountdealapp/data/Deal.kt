package com.zybooks.discountdealapp.data

data class Deal(
    val id: Int,
    val title: String,
    val description: String,
    val discountAmount: Double,
    val storeName: String,
    val category: String,
    val isOnline: Boolean,
    val location: String,
    val validUntil: String,
    val imageUrl: String? = null,   // nullable
    val latitude: Double,
    val longitude: Double,
    val websiteUrl: String? = null  // nullable
)
