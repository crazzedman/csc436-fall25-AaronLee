package com.zybooks.discountdealapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.ui.viewmodels.NearbyDealsViewModel
import com.zybooks.discountdealapp.ui.viewmodels.ProfileViewModel

fun openGoogleMaps(context: Context, lat: Double, lng: Double, label: String) {
    val uri = "geo:$lat,$lng?q=$lat,$lng($label)".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyDealsScreen(
    nearbyVM: NearbyDealsViewModel,
    profileVM: ProfileViewModel,
    deals: List<Deal>,
    onDealClick: (Deal) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    // Update user location if permission granted
    LaunchedEffect(profileVM.locationPermissionGranted) {
        if (profileVM.locationPermissionGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        nearbyVM.setUserLocation(location.latitude, location.longitude)
                    }
                }
            } catch (e: SecurityException) {
                // Ignore if permission not granted
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nearby Deals") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NearbyDealsMap(
                deals = deals,
                userLat = nearbyVM.userLatitude,
                userLng = nearbyVM.userLongitude
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(deals) { deal ->
                    DealListItem(deal = deal, onDealClick = onDealClick, context = context)
                }
            }
        }
    }
}

@Composable
fun NearbyDealsMap(
    deals: List<Deal>,
    userLat: Double,
    userLng: Double
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(com.google.android.gms.maps.model.LatLng(userLat, userLng), 12f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        cameraPositionState = cameraPositionState
    ) {
        // User location marker in blue
        Marker(
            state = rememberUpdatedMarkerState(position = com.google.android.gms.maps.model.LatLng(userLat, userLng)),
            title = "You are here",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        )

        // Deal markers in red
        deals.forEach { deal ->
            Marker(
                state = rememberUpdatedMarkerState(position = com.google.android.gms.maps.model.LatLng(deal.latitude, deal.longitude)),
                title = deal.title,
                snippet = deal.storeName,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        }
    }
}

@Composable
fun DealListItem(deal: Deal, onDealClick: (Deal) -> Unit, context: Context) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .padding(vertical = 4.dp),
        onClick = { onDealClick(deal) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = deal.title, style = MaterialTheme.typography.titleMedium)
            Text(text = deal.storeName, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Discount: ${deal.discountAmount}%", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { openGoogleMaps(context, deal.latitude, deal.longitude, deal.title) }) {
                Text("Open in Google Maps")
            }
        }
    }
}
