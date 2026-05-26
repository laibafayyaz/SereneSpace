package com.example.serenespace.ui.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.serenespace.LanguageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isReminderOn by remember { mutableStateOf(false) }

    var selectedLanguageCode by remember { mutableStateOf(LanguageManager.getLanguage(context)) }
    var languageMenuExpanded by remember { mutableStateOf(false) }

    val selectedLanguageName = LanguageManager.supportedLanguages.find { it.code == selectedLanguageCode }?.displayName ?: "English"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
            // Dark Mode Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Dark Mode", style = MaterialTheme.typography.titleMedium)
                    Text("Reduce eye strain in low light", style = MaterialTheme.typography.bodySmall)
                }
                Switch(checked = isDarkMode, onCheckedChange = onDarkModeChanged)
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // Language Selector
            Text("App Language", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedButton(
                    onClick = { languageMenuExpanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedLanguageName)
                }
                DropdownMenu(
                    expanded = languageMenuExpanded,
                    onDismissRequest = { languageMenuExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f) // Makes the dropdown wide
                ) {
                    // Automatically generates the list from our LanguageManager
                    LanguageManager.supportedLanguages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(language.displayName) },
                            onClick = {
                                selectedLanguageCode = language.code
                                LanguageManager.saveLanguage(context, language.code)

                                // The Magic Line: This changes the app language instantly and safely!
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags(language.code)
                                )

                                languageMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // Daily Reminder Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Daily Reminders", style = MaterialTheme.typography.titleMedium)
                    Text("Get a daily notification to check in", style = MaterialTheme.typography.bodySmall)
                }
                Switch(checked = isReminderOn, onCheckedChange = { isReminderOn = it })
            }
        }
    }
}