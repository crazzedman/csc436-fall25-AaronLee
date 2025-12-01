package com.zybooks.discountdealapp.data

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
            location = "Best Buy – Local Store",
            validUntil = "2025-12-31",
            imageUrl = null,
            latitude = 37.7749,
            longitude = -122.4194,
            websiteUrl = null
        ),
        Deal(
            id = 2,
            title = "30% Off Headphones",
            description = "Sony, Bose, Audio-Technica.",
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

        // ---------- FOOD ----------
        Deal(
            id = 3,
            title = "BOGO Burritos",
            description = "Buy 1 get 1 free entrée.",
            discountAmount = 50.0,
            storeName = "Chipotle",
            category = "Food",
            isOnline = false,
            location = "Chipotle – Local Store",
            validUntil = "2025-10-10",
            imageUrl = null,
            latitude = 37.7833,
            longitude = -122.4090,
            websiteUrl = null
        ),
        Deal(
            id = 4,
            title = "Free Drink with Purchase",
            description = "Any handcrafted Starbucks drink.",
            discountAmount = 100.0,
            storeName = "Starbucks",
            category = "Food",
            isOnline = false,
            location = "Starbucks Cafe",
            validUntil = "2025-07-01",
            imageUrl = null,
            latitude = 37.7812,
            longitude = -122.4121,
            websiteUrl = null
        ),

        // ---------- CLOTHING ----------
        Deal(
            id = 5,
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
            id = 6,
            title = "40% Off Apparel",
            description = "Seasonal clearance.",
            discountAmount = 40.0,
            storeName = "Target",
            category = "Clothing",
            isOnline = false,
            location = "Target – Local Store",
            validUntil = "2025-09-20",
            imageUrl = null,
            latitude = 37.7860,
            longitude = -122.4192,
            websiteUrl = null
        ),

        // ---------- BEAUTY ----------
        Deal(
            id = 7,
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

        // ---------- HOME ----------
        Deal(
            id = 8,
            title = "30% Off Furniture",
            description = "Living room & bedroom.",
            discountAmount = 30.0,
            storeName = "IKEA",
            category = "Home",
            isOnline = false,
            location = "IKEA – Local Store",
            validUntil = "2025-08-15",
            imageUrl = null,
            latitude = 37.7000,
            longitude = -122.4660,
            websiteUrl = null
        ),
        Deal(
            id = 9,
            title = "10% Off Bulk Groceries",
            description = "Members save even more.",
            discountAmount = 10.0,
            storeName = "Costco",
            category = "Home",
            isOnline = false,
            location = "Costco Warehouse",
            validUntil = "2025-12-10",
            imageUrl = null,
            latitude = 37.8051,
            longitude = -122.4153,
            websiteUrl = null
        )
    )


    // ----------------------------
    // SIMPLE REPOSITORY FUNCTIONS
    // ----------------------------

    fun getOnlineDeals(): List<Deal> =
        deals.filter { it.isOnline }

    fun getNearbyDeals(): List<Deal> =
        deals.filter { !it.isOnline }

    fun getFeaturedDeals(limit: Int = 5): List<Deal> =
        deals.sortedByDescending { it.discountAmount }.take(limit)

    fun getDealById(id: Int): Deal? =
        deals.find { it.id == id }

    fun getAllDeals(): List<Deal> = deals
}
