package com.example.serenespace.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.serenespace.viewmodel.MoodViewModel // FIXED IMPORT
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyPlanScreen(viewModel: MoodViewModel, onBack: () -> Unit) {
    val currentPlan by viewModel.safetyPlan.collectAsState(initial = null)

    var warningSigns by remember { mutableStateOf(currentPlan?.warningSigns ?: "") }
    var copingStrategies by remember { mutableStateOf(currentPlan?.copingStrategies ?: "") }
    var supportContacts by remember { mutableStateOf(currentPlan?.supportContacts ?: "") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentPlan) {
        currentPlan?.let {
            warningSigns = it.warningSigns
            copingStrategies = it.copingStrategies
            supportContacts = it.supportContacts
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("My Safety Plan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            Text("Create a plan to keep yourself safe during a crisis. Fill this out when you are feeling calm.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(value = warningSigns, onValueChange = { warningSigns = it }, label = { Text("1. Warning Signs") }, placeholder = { Text("e.g., Feeling hopeless, withdrawing from friends") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = copingStrategies, onValueChange = { copingStrategies = it }, label = { Text("2. Coping Strategies") }, placeholder = { Text("e.g., Listen to music, go for a walk, do breathing exercises") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = supportContacts, onValueChange = { supportContacts = it }, label = { Text("3. Support Contacts") }, placeholder = { Text("e.g., Mom (555-0199), Therapist (555-0123)") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.saveSafetyPlan(warningSigns, copingStrategies, supportContacts)
                    scope.launch { snackbarHostState.showSnackbar("Safety Plan Saved! 🛡️") }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = "Save")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Safety Plan")
            }
        }
    }
}