package com.example.serenespace.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenespace.viewmodel.MoodViewModel // FIXED IMPORT
import com.example.serenespace.data.MoodEntity // FIXED IMPORT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodHistoryScreen(viewModel: MoodViewModel, onBack: () -> Unit) {
    val moodHistory by viewModel.moodHistory.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mood History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (moodHistory.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No moods saved yet. Go back and check in!", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(moodHistory) { mood -> MoodCard(mood) }
            }
        }
    }
}

@Composable
fun MoodCard(mood: MoodEntity) {
    val date = Date(mood.timestamp)
    val formattedDate = SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.getDefault()).format(date)

    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(mood.emoji, fontSize = 36.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(mood.moodType, style = MaterialTheme.typography.titleMedium)
                if (mood.note.isNotBlank()) { Text(mood.note, style = MaterialTheme.typography.bodyMedium) }
                Text(formattedDate, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}