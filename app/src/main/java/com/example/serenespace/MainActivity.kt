package com.example.serenespace

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.serenespace.data.AppDatabase
import com.example.serenespace.repository.MoodRepository
import com.example.serenespace.ui.screens.*
import com.example.serenespace.viewmodel.MoodViewModel
import com.example.serenespace.viewmodel.MoodViewModelFactory

class MainActivity : AppCompatActivity() { // CHANGED TO AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        createNotificationChannel()
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val repository = MoodRepository(db.moodDao(), db.journalDao(), db.safetyPlanDao())
        val factory = MoodViewModelFactory(repository)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            MaterialTheme(
                colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: MoodViewModel = viewModel(factory = factory)

                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") { SplashScreen(navController = navController) }
                        composable("home") {
                            MoodCheckInScreen(
                                viewModel = viewModel,
                                onCheckInComplete = { },
                                onNavigateToBreathing = { navController.navigate("breathing") },
                                onNavigateToHistory = { navController.navigate("history") },
                                onNavigateToJournal = { navController.navigate("journal") },
                                onNavigateToJournalHistory = { navController.navigate("journal_history") },
                                onNavigateToCrisis = { navController.navigate("crisis") },
                                onNavigateToSettings = { navController.navigate("settings") }
                            )
                        }
                        composable("breathing") { BreathingScreen(onBack = { navController.popBackStack() }) }
                        composable("history") { MoodHistoryScreen(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("journal") { JournalScreen(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("journal_history") { JournalHistoryScreen(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("crisis") { CrisisScreen(onBack = { navController.popBackStack() }, onNavigateToSafetyPlan = { navController.navigate("safety_plan") }) }
                        composable("safety_plan") { SafetyPlanScreen(viewModel = viewModel, onBack = { navController.popBackStack() }) }
                        composable("settings") {
                            SettingsScreen(
                                isDarkMode = isDarkMode,
                                onDarkModeChanged = { newState -> isDarkMode = newState },
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("serene_channel", "SereneSpace Reminders", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Daily Reminder Notifications"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}