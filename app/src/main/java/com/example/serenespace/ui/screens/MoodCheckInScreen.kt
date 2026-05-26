package com.example.serenespace.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenespace.R // THIS IS THE LINE THAT FIXES THE RED ERRORS!
import com.example.serenespace.viewmodel.MoodViewModel
import kotlinx.coroutines.launch

data class MoodOption(val emoji: String, val label: String, val isNegative: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodCheckInScreen(
    viewModel: MoodViewModel,
    onCheckInComplete: () -> Unit,
    onNavigateToBreathing: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToJournalHistory: () -> Unit,
    onNavigateToCrisis: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val strHappy = stringResource(R.string.happy)
    val strNeutral = stringResource(R.string.neutral)
    val strSad = stringResource(R.string.sad)
    val strAnxious = stringResource(R.string.anxious)
    val strAngry = stringResource(R.string.angry)

    val moods = listOf(
        MoodOption("😊", strHappy, false),
        MoodOption("😐", strNeutral, false),
        MoodOption("😢", strSad, true),
        MoodOption("😫", strAnxious, true),
        MoodOption("😡", strAngry, true)
    )

    var selectedMood by remember { mutableStateOf<MoodOption?>(null) }
    var note by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val savedMessage = stringResource(R.string.mood_saved)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.home_prompt), style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(moods) { mood ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            border = BorderStroke(
                                width = if (selectedMood == mood) 3.dp else 1.dp,
                                color = if (selectedMood == mood) MaterialTheme.colorScheme.primary else Color.Gray
                            ),
                            modifier = Modifier.padding(4.dp),
                            onClick = { selectedMood = mood }
                        ) {
                            Text(mood.emoji, fontSize = 40.sp, modifier = Modifier.padding(8.dp))
                        }
                        Text(mood.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text(stringResource(R.string.note_hint)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedMood?.isNegative == true) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.need_help_title), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(stringResource(R.string.need_help_body), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    selectedMood?.let {
                        viewModel.saveMood(it.emoji, it.label, note)
                        selectedMood = null
                        note = ""
                        scope.launch { snackbarHostState.showSnackbar(savedMessage) }
                    }
                },
                enabled = selectedMood != null
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.save_mood))
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(onClick = onNavigateToBreathing, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Air, contentDescription = "Breathing"); Spacer(modifier = Modifier.width(8.dp)); Text(stringResource(R.string.breathing))
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(onClick = onNavigateToHistory, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.DateRange, contentDescription = "History"); Spacer(modifier = Modifier.width(8.dp)); Text(stringResource(R.string.history))
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(onClick = onNavigateToJournal, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Edit, contentDescription = "Journal"); Spacer(modifier = Modifier.width(8.dp)); Text(stringResource(R.string.write_journal))
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(onClick = onNavigateToJournalHistory, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Book, contentDescription = "Journal History"); Spacer(modifier = Modifier.width(8.dp)); Text(stringResource(R.string.read_journal))
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onNavigateToCrisis, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Icon(Icons.Default.HealthAndSafety, contentDescription = "Crisis"); Spacer(modifier = Modifier.width(8.dp)); Text(stringResource(R.string.crisis))
            }
        }
    }
}