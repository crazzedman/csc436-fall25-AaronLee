package com.zybooks.discountdealapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    var showLocationDialog by remember { mutableStateOf(false) }

    // Check if we should show location dialog
    LaunchedEffect(profileVM.locationPermissionGranted, profileVM.shouldAskLocationPermission) {
        if (!profileVM.locationPermissionGranted &&
            profileVM.shouldAskLocationPermission &&
            !profileVM.neverAskAgain
        ) {
            showLocationDialog = true
        } else {
            // Load user location if allowed
            if (profileVM.locationPermissionGranted) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            nearbyVM.setUserLocation(location.latitude, location.longitude)
                        }
                    }
                } catch (_: SecurityException) {}
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            nearbyVM.saveScrollState(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset
            )
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

            // Map
            NearbyDealsMap(
                deals = deals,
                userLat = nearbyVM.userLatitude,
                userLng = nearbyVM.userLongitude,
                showUserMarker = profileVM.locationPermissionGranted
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(deals) { deal ->
                    DealListItem(
                        deal = deal,
                        onDealClick = onDealClick,
                        onOpenMap = {
                            openGoogleMaps(
                                context,
                                deal.latitude,
                                deal.longitude,
                                deal.title
                            )
                        }
                    )
                }
            }


        }

        // Location permission dialog
        // Location permission dialog
        if (showLocationDialog) {
            var dontAskAgain by remember { mutableStateOf(false) }

            AlertDialog(
                onDismissRequest = { showLocationDialog = false },
                title = { Text("Enable Location Access?") },
                text = {
                    Column {
                        Text("This helps show nearby deals around you.")
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = dontAskAgain,
                                onCheckedChange = { checked ->
                                    dontAskAgain = checked
                                    // Update ViewModel immediately
                                    profileVM.updateNeverAskAgain(checked)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Don't ask again")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        profileVM.setLocationPermission(true)
                        showLocationDialog = false
                    }) {
                        Text("Enable")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        profileVM.setLocationPermission(false)
                        showLocationDialog = false
                    }) {
                        Text("No, thanks")
                    }
                }
            )
        }

    }
}

@Composable
fun NearbyDealsMap(
    deals: List<Deal>,
    userLat: Double,
    userLng: Double,
    showUserMarker: Boolean
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            com.google.android.gms.maps.model.LatLng(userLat, userLng),
            12f
        )
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        cameraPositionState = cameraPositionState
    ) {
        // User location marker in blue
        if (showUserMarker) {
            Marker(
                state = rememberUpdatedMarkerState(
                    position = com.google.android.gms.maps.model.LatLng(userLat, userLng)
                ),
                title = "You are here",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
            )
        }

        // Deal markers in red
        deals.forEach { deal ->
            Marker(
                state = rememberUpdatedMarkerState(
                    position = com.google.android.gms.maps.model.LatLng(deal.latitude, deal.longitude)
                ),
                title = deal.title,
                snippet = deal.storeName,
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
        }
    }
}

@Composable
fun DealListItem(
    deal: Deal,
    onDealClick: (Deal) -> Unit,
    onOpenMap: () -> Unit
) {
    Card(
        onClick = { onDealClick(deal) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                text = deal.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = deal.storeName,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Discount: ${deal.discountAmount}%",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(10.dp))

            Button(onClick = onOpenMap) {
                Text("Open In Google Maps")
            }

        }
    }
}
