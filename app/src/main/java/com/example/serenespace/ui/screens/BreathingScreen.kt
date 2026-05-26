package com.example.serenespace.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreathingScreen(onBack: () -> Unit) {
    var isRunning by remember { mutableStateOf(false) }
    var breathPhase by remember { mutableStateOf("Tap Start to Begin") }

    // 1. Determine the TARGET scale based on the phase
    val targetScale = when {
        isRunning && breathPhase.startsWith("Inhale") -> 1.5f // Expand
        isRunning && breathPhase.startsWith("Hold") -> 1.5f   // Stay expanded
        isRunning && breathPhase.startsWith("Exhale") -> 0.8f // Contract
        else -> 1f // Default idle size
    }

    // 2. Match the animation duration to the actual breathing time
    val animationDuration = when {
        breathPhase.startsWith("Inhale") -> 4000
        breathPhase.startsWith("Hold") -> 7000
        breathPhase.startsWith("Exhale") -> 8000
        else -> 500 // Fast return when stopping
    }

    // 3. Animate the scale smoothly
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(durationMillis = animationDuration, easing = EaseInOut),
        label = "BreathScale"
    )

    // Breathing Logic
    LaunchedEffect(isRunning) {
        while (isRunning) {
            breathPhase = "Inhale (4s)"; delay(4000)
            breathPhase = "Hold (7s)"; delay(7000)
            breathPhase = "Exhale (8s)"; delay(8000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guided Breathing") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = breathPhase, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(40.dp))

            // 4. Animate the color based on the breath phase
            val circleColor by animateColorAsState(
                targetValue = when {
                    isRunning && breathPhase.startsWith("Inhale") -> Color(0xFF80CBC4) // Bright Teal
                    isRunning && breathPhase.startsWith("Hold") -> Color(0xFFB39DDB) // Calming Purple
                    isRunning && breathPhase.startsWith("Exhale") -> Color(0xFF90CAF9) // Relaxing Blue
                    else -> Color(0xFFB2DFDB) // Default Teal
                },
                animationSpec = tween(1000), label = "CircleColor"
            )

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .scale(scale) // This now expands AND contracts!
                    .background(circleColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("4-7-8", fontSize = 24.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {
                isRunning = !isRunning
                if (!isRunning) breathPhase = "Tap Start to Begin"
            }) {
                Text(if (isRunning) "Stop" else "Start Breathing")
            }
        }
    }
}