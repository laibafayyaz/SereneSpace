package com.example.serenespace.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Wait for 2.5 seconds, then navigate to home
    LaunchedEffect(key1 = true) {
        delay(2500)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // The UI of the Splash Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F2F1)), // Light Teal Background
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Beautiful Emoji Logo (Crash-proof!)
        Text(
            text = "🌿",
            fontSize = 80.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // App Name
        Text(
            text = "SereneSpace",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00695C) // Dark Teal
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tagline
        Text(
            text = "Mental Health Check-in & Support",
            fontSize = 16.sp,
            color = Color(0xFF4DB6AC) // Medium Teal
        )
    }
}