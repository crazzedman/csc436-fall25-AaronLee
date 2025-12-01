package com.zybooks.discountdealapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeVM: HomeViewModel,
    onNearbyClick: () -> Unit,
    onOnlineClick: () -> Unit,
    onProfileClick: () -> Unit,
    onDealClick: (Deal) -> Unit
) {
    // Directly read from mutableStateOf in ViewModel
    val isLoading = homeVM.isLoading
    val totalDeals = homeVM.totalDealsCount
    val categories = homeVM.categories

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top app bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Color(0xFF2ECC71))
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "The Daily Dealer",
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Loading state
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            // Total deals
            Text(
                text = "Total Deals: $totalDeals",
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            if (categories.isNotEmpty()) {
                Text(
                    text = "Categories: ${categories.joinToString(", ")}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Home cards
        HomeCard(
            label = "Nearby Deals",
            icon = Icons.Default.Map,
            onClick = onNearbyClick
        )

        HomeCard(
            label = "Online Deals",
            icon = Icons.Default.Public,
            onClick = onOnlineClick
        )

        HomeCard(
            label = "Profile",
            icon = Icons.Default.Person,
            onClick = onProfileClick
        )
    }
}

@Composable
fun HomeCard(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(2.dp, Color(0xFF4EA7F7))
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 22.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = label, fontSize = 18.sp, color = Color.Black)
        }
    }
}
