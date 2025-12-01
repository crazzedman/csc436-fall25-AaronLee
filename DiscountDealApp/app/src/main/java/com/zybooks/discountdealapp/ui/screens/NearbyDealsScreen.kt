package com.zybooks.discountdealapp.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.maps.android.compose.*
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.ui.viewmodels.NearbyDealsViewModel


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
    deals: List<Deal>,
    onDealClick: (Deal) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = nearbyVM.scrollIndex,
        initialFirstVisibleItemScrollOffset = nearbyVM.scrollOffset
    )

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

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {

//            item {
//                NearbyDealsMap(
//                    deals = deals,
//                    userLat = nearbyVM.userLatitude,
//                    userLng = nearbyVM.userLongitude
//                )
//            }

            items(deals) { deal ->
                DealListItemWithMapButton(
                    deal = deal,
                    onDealClick = onDealClick,
                    context = context
                )
            }
        }
    }
}


//@Composable
//fun NearbyDealsMap(
//    deals: List<Deal>,
//    userLat: Double,
//    userLng: Double
//) {
//    val userLatLng = com.google.android.gms.maps.model.LatLng(userLat, userLng)
//
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(userLatLng, 12f)
//    }
//    val userMarkerState = rememberUpdatedMarkerState(position = userLatLng)
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(250.dp),
//        elevation = CardDefaults.cardElevation(6.dp)
//    ) {
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState,
//            uiSettings = MapUiSettings(zoomControlsEnabled = false)
//        ) {
//
//            Marker(
//                state = userMarkerState,
//                title = "You are here"
//            )
//
//            deals.forEach { deal ->
//                val dealLatLng = com.google.android.gms.maps.model.LatLng(
//                    deal.latitude,
//                    deal.longitude
//                )
//
//                val dealMarkerState = rememberUpdatedMarkerState(position = dealLatLng)
//
//                Marker(
//                    state = dealMarkerState,
//                    title = deal.title,
//                    snippet = deal.storeName
//                )
//            }
//        }
//    }
//}
//

@Composable
fun DealListItemWithMapButton(
    deal: Deal,
    onDealClick: (Deal) -> Unit,
    context: Context
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onDealClick(deal) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(deal.title, style = MaterialTheme.typography.titleMedium)
            Text(deal.storeName, style = MaterialTheme.typography.bodyMedium)
            Text("Discount: ${deal.discountAmount}", style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    openGoogleMaps(
                        context = context,
                        lat = deal.latitude,
                        lng = deal.longitude,
                        label = deal.title
                    )
                }
            ) {
                Text("Open in Google Maps")
            }
        }
    }
}
