package com.zybooks.discountdealapp.ui.screens

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
import com.zybooks.discountdealapp.data.Deal
import com.zybooks.discountdealapp.ui.viewmodels.OnlineDealsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineDealsScreen(
    onlineVM: OnlineDealsViewModel,
    onDealClick: (Deal) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Null-safe deal list
    val filtered = onlineVM.filteredDeals

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = onlineVM.scrollIndex,
        initialFirstVisibleItemScrollOffset = onlineVM.scrollOffset
    )

    DisposableEffect(onlineVM) {
        onDispose {
            onlineVM.saveScrollState(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Online Deals") },
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

            CategoryDropdown(
                categories = onlineVM.categories,
                selected = onlineVM.selectedCategory,
                onSelect = { onlineVM.filterByCategory(it) }
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn(state = listState) {
                items(filtered) { deal ->

                    val title = deal.title
                    val description = deal.description
                    val url = deal.websiteUrl

                    Card(
                        onClick = { onDealClick(deal) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {

                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            if (!url.isNullOrBlank()) {
                                Spacer(Modifier.height(6.dp))
                                OutlinedButton(
                                    onClick = {
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                                            context.startActivity(intent)
                                        } catch (_: Exception) {
                                            // Safe fail: do nothing
                                        }
                                    }
                                ) {
                                    Text("Visit Website")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CategoryDropdown(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (categories.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No categories") },
                    onClick = { expanded = false }
                )
            } else {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            expanded = false
                            onSelect(category)
                        }
                    )
                }
            }
        }
    }
}
