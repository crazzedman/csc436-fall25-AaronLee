package com.zybooks.discountdealapp.data

import kotlin.math.*

class DealRepository {

    private val deals = listOf(
        // ---------- ELECTRONICS ----------
        Deal(
            id = 1,
            title = "20% Off Laptops",
            description = "Select gaming and work laptops.",
            discountAmount = 20.0,
            storeName = "Best Buy",
            category = "Electronics",
            isOnline = false,
            location = "Best Buy – SLO",
            validUntil = "2025-12-31",
            imageUrl = null,
            latitude = 35.2828,
            longitude = -120.6596,
            websiteUrl = null
        ),
        Deal(
            id = 2,
            title = "30% Off Headphones",
            description = "Sony, Bose, Audio-Technical.",
            discountAmount = 30.0,
            storeName = "Amazon",
            category = "Electronics",
            isOnline = true,
            location = "Online",
            validUntil = "2025-11-30",
            imageUrl = null,
            latitude = 0.0,
            longitude = 0.0,
            websiteUrl = "https://amazon.com"
        ),
        Deal(
            id = 3,
            title = "15% Off Phone Accessories",
            description = "Cables, chargers, cases, and more.",
            discountAmount = 15.0,
            storeName = "TechZone",
            category = "Electronics",
            isOnline = false,
            location = "TechZone – SLO",
            validUntil = "2025-10-15",
            imageUrl = null,
            latitude = 35.2840,
            longitude = -120.6605,
            websiteUrl = null
        ),

        // ---------- FOOD ----------
        Deal(
            id = 4,
            title = "BOGO Burritos",
            description = "Buy 1 get 1 free entrée.",
            discountAmount = 50.0,
            storeName = "Chipotle",
            category = "Food",
            isOnline = false,
            location = "Chipotle – SLO",
            validUntil = "2025-10-10",
            imageUrl = null,
            latitude = 35.2835,
            longitude = -120.6600,
            websiteUrl = null
        ),
        Deal(
            id = 5,
            title = "Free Drink with Purchase",
            description = "Any handcrafted Starbucks drink.",
            discountAmount = 100.0,
            storeName = "Starbucks",
            category = "Food",
            isOnline = false,
            location = "Starbucks – SLO",
            validUntil = "2025-07-01",
            imageUrl = null,
            latitude = 35.2850,
            longitude = -120.6620,
            websiteUrl = null
        ),
        Deal(
            id = 6,
            title = "30% Off Pizza",
            description = "Large pizza with 2 toppings.",
            discountAmount = 30.0,
            storeName = "Mario's Pizza",
            category = "Food",
            isOnline = false,
            location = "Mario's Pizza – SLO",
            validUntil = "2025-12-15",
            imageUrl = null,
            latitude = 35.2805,
            longitude = -120.6580,
            websiteUrl = null
        ),

        // ---------- CLOTHING ----------
        Deal(
            id = 7,
            title = "25% Off All Shoes",
            description = "Nike Members exclusive.",
            discountAmount = 25.0,
            storeName = "Nike",
            category = "Clothing",
            isOnline = true,
            location = "Online",
            validUntil = "2025-12-31",
            imageUrl = null,
            latitude = 0.0,
            longitude = 0.0,
            websiteUrl = "https://nike.com"
        ),
        Deal(
            id = 8,
            title = "40% Off Apparel",
            description = "Seasonal clearance.",
            discountAmount = 40.0,
            storeName = "Target",
            category = "Clothing",
            isOnline = false,
            location = "Target – SLO",
            validUntil = "2025-09-20",
            imageUrl = null,
            latitude = 35.2830,
            longitude = -120.6610,
            websiteUrl = null
        ),
        Deal(
            id = 9,
            title = "50% Off Jackets",
            description = "Winter collection.",
            discountAmount = 50.0,
            storeName = "Fashion Hub",
            category = "Clothing",
            isOnline = false,
            location = "Fashion Hub – SLO",
            validUntil = "2025-11-30",
            imageUrl = null,
            latitude = 35.2815,
            longitude = -120.6590,
            websiteUrl = null
        ),

        // ---------- BEAUTY ----------
        Deal(
            id = 10,
            title = "15% Off Makeup",
            description = "Top brands included.",
            discountAmount = 15.0,
            storeName = "Sephora",
            category = "Beauty",
            isOnline = true,
            location = "Online",
            validUntil = "2025-11-05",
            imageUrl = null,
            latitude = 0.0,
            longitude = 0.0,
            websiteUrl = "https://sephora.com"
        ),
        Deal(
            id = 11,
            title = "Free Haircut",
            description = "New customers only.",
            discountAmount = 100.0,
            storeName = "Salon Lux",
            category = "Beauty",
            isOnline = false,
            location = "Salon Lux – SLO",
            validUntil = "2025-08-20",
            imageUrl = null,
            latitude = 35.2820,
            longitude = -120.6608,
            websiteUrl = null
        ),

        // ---------- HOME ----------
        Deal(
            id = 12,
            title = "30% Off Furniture",
            description = "Living room & bedroom.",
            discountAmount = 30.0,
            storeName = "IKEA",
            category = "Home",
            isOnline = false,
            location = "IKEA – SLO",
            validUntil = "2025-08-15",
            imageUrl = null,
            latitude = 35.2855,
            longitude = -120.6595,
            websiteUrl = null
        ),
        Deal(
            id = 13,
            title = "10% Off Bulk Groceries",
            description = "Members save even more.",
            discountAmount = 10.0,
            storeName = "Costco",
            category = "Home",
            isOnline = false,
            location = "Costco – SLO",
            validUntil = "2025-12-10",
            imageUrl = null,
            latitude = 35.2845,
            longitude = -120.6602,
            websiteUrl = null
        ),

        // ---------- SPORTS ----------
        Deal(
            id = 14,
            title = "50% Off Running Shoes",
            description = "Select brands only.",
            discountAmount = 50.0,
            storeName = "SportMax",
            category = "Sports",
            isOnline = true,
            location = "Online",
            validUntil = "2025-10-30",
            imageUrl = null,
            latitude = 0.0,
            longitude = 0.0,
            websiteUrl = "https://sportmax.com"
        ),
        Deal(
            id = 15,
            title = "20% Off Yoga Mats",
            description = "Eco-friendly mats.",
            discountAmount = 20.0,
            storeName = "FitLife",
            category = "Sports",
            isOnline = false,
            location = "FitLife – SLO",
            validUntil = "2025-09-10",
            imageUrl = null,
            latitude = 35.2838,
            longitude = -120.6598,
            websiteUrl = null
        ),

        // Additional deals (16-20)
        Deal(
            id = 16,
            title = "15% Off Coffee Beans",
            description = "Local roasters.",
            discountAmount = 15.0,
            storeName = "Bean & Brew",
            category = "Food",
            isOnline = false,
            location = "Bean & Brew – SLO",
            validUntil = "2025-11-05",
            imageUrl = null,
            latitude = 35.2822,
            longitude = -120.6612,
            websiteUrl = null
        ),
        Deal(
            id = 17,
            title = "25% Off Kitchen Appliances",
            description = "Blenders, toasters, etc.",
            discountAmount = 25.0,
            storeName = "Home Haven",
            category = "Home",
            isOnline = false,
            location = "Home Haven – SLO",
            validUntil = "2025-12-05",
            imageUrl = null,
            latitude = 35.2837,
            longitude = -120.6609,
            websiteUrl = null
        ),
        Deal(
            id = 18,
            title = "Buy 1 Get 1 Ice Cream",
            description = "Local ice cream shop.",
            discountAmount = 50.0,
            storeName = "Sweet Treats",
            category = "Food",
            isOnline = false,
            location = "Sweet Treats – SLO",
            validUntil = "2025-07-30",
            imageUrl = null,
            latitude = 35.2810,
            longitude = -120.6599,
            websiteUrl = null
        ),
        Deal(
            id = 19,
            title = "30% Off Sunglasses",
            description = "Stylish shades for summer.",
            discountAmount = 30.0,
            storeName = "SunWear",
            category = "Clothing",
            isOnline = false,
            location = "SunWear – SLO",
            validUntil = "2025-08-25",
            imageUrl = null,
            latitude = 35.2842,
            longitude = -120.6601,
            websiteUrl = null
        ),
        Deal(
            id = 20,
            title = "20% Off Fitness Gear",
            description = "Weights, resistance bands, etc.",
            discountAmount = 20.0,
            storeName = "FitGear",
            category = "Sports",
            isOnline = false,
            location = "FitGear – SLO",
            validUntil = "2025-12-01",
            imageUrl = null,
            latitude = 35.2825,
            longitude = -120.6597,
            websiteUrl = null
        )
    )

    // ----------------------------
    // REPOSITORY FUNCTIONS
    // ----------------------------

    fun getOnlineDeals(): List<Deal> =
        deals.filter { it.isOnline }

    fun getNearbyDeals(
        userLat: Double = 35.2828,
        userLng: Double = -120.6596,
        maxDistanceMiles: Double = 10.0
    ): List<Deal> =
        deals.filter { !it.isOnline }.filter { deal ->
            val distance = calculateDistanceMiles(userLat, userLng, deal.latitude, deal.longitude)
            distance <= maxDistanceMiles
        }

    fun getFeaturedDeals(limit: Int = 5): List<Deal> =
        deals.sortedByDescending { it.discountAmount }.take(limit)

    fun getDealById(id: Int): Deal? =
        deals.find { it.id == id }

    fun getAllDeals(): List<Deal> = deals

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
