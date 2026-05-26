package com.example.serenespace.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serenespace.viewmodel.MoodViewModel // FIXED IMPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(viewModel: MoodViewModel, onBack: () -> Unit) {
    var content by remember { mutableStateOf("") }
    var moodTag by remember { mutableStateOf("Reflective") }
    val tags = listOf("Reflective", "Grateful", "Anxious", "Sad", "Happy")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Private Journal") },
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
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("How are you reflecting today?", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Mood Tag Selector
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.forEach { tag ->
                    FilterChip(
                        selected = (moodTag == tag),
                        onClick = { moodTag = tag },
                        label = { Text(tag) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Journal Text Field
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Write your thoughts... (Encrypted & Private)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (content.isNotBlank()) {
                        viewModel.saveJournal(content, moodTag)
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = content.isNotBlank()
            ) {
                Text("Save Encrypted Entry 🔒")
            }
        }
    }
}