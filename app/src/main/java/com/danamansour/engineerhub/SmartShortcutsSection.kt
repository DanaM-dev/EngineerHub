package com.danamansour.engineerhub

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ShortcutItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val isPinned: Boolean = false
)

@Composable
fun SmartShortcutsSection(
    recentShortcuts: List<ShortcutItem> = emptyList(),
    onShortcutClick: (ShortcutItem) -> Unit
) {
    val pinnedShortcuts = remember {
        listOf(
            ShortcutItem("converter", "Converter", Icons.Default.SwapHoriz, isPinned = true),
            ShortcutItem("telecom", "Telecom", Icons.Default.Calculate, isPinned = true)
        )
    }

    // + filtering any duplicates
    val combinedShortcuts = remember(recentShortcuts) {
        val pinnedIds = pinnedShortcuts.map { it.id }.toSet()
        val filteredRecents = recentShortcuts.filterNot { it.id in pinnedIds }.take(3)
        pinnedShortcuts + filteredRecents
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Quick Tools",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(combinedShortcuts) { shortcut ->
                ShortcutCard(
                    shortcut = shortcut,
                    onClick = { onShortcutClick(shortcut) }
                )
            }
        }
    }
}

@Composable
fun ShortcutCard(
    shortcut: ShortcutItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.size(width = 115.dp, height = 95.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            //tiny badge tag for recent tools
            if (!shortcut.isPinned) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "RECENT",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = shortcut.icon,
                    contentDescription = shortcut.title,
                    tint = CalendarHighlight,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = shortcut.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}