package com.zybooks.discountdealapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.zybooks.discountdealapp.data.Deal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealDetailsScreen(
    deal: Deal?,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(deal?.title ?: "Deal Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when {
            deal == null -> DealDetailsError(padding)
            else -> DealDetailsContent(deal, padding)
        }
    }
}

@Composable
private fun DealDetailsError(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unable to load deal details.",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Please try again.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DealDetailsContent(
    deal: Deal,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // IMAGE — safe fallback
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            AsyncImage(
                model = deal.imageUrl ?: "",
                contentDescription = deal.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TITLE
        Text(
            text = deal.title ?: "Untitled Deal",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        // STORE
        DetailField(label = "Store", value = deal.storeName)

        // CATEGORY
        DetailField(label = "Category", value = deal.category)

        // DISCOUNT
        DetailField(
            label = "Discount",
            value = deal.discountAmount?.let { "$it%" }
        )

        // VALID UNTIL
        DetailField(label = "Valid until", value = deal.validUntil)

        Spacer(Modifier.height(10.dp))

        // DESCRIPTION
        if (!deal.description.isNullOrBlank()) {
            Text(
                text = deal.description!!,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DetailField(label: String, value: String?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value ?: "N/A",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
