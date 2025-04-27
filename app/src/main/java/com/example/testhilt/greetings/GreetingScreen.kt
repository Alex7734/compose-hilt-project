package com.example.testhilt.greetings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testhilt.greetings.components.AnimatedDecorations
import com.example.testhilt.greetings.components.FeaturesSection
import com.example.testhilt.greetings.components.WelcomeSection

@Composable
fun GreetingScreen(navController: NavController) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.background
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeSection()

            Spacer(modifier = Modifier.height(48.dp))

            FeaturesSection()

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedDecorations()
        }
    }
}
