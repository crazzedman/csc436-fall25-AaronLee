package com.zybooks.discountdealapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zybooks.discountdealapp.ui.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileVM: ProfileViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = profileVM.darkModeEnabled,
                    onCheckedChange = { profileVM.setDarkMode(it) }
                )
            }

            Spacer(Modifier.height(16.dp))

            // Location permission toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Location Access", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = profileVM.locationPermissionGranted,
                    onCheckedChange = { profileVM.setLocationPermission(it) }
                )
            }

            Spacer(Modifier.height(16.dp))

            // App version display
            Text(
                text = "App Version: ${profileVM.appVersion}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

