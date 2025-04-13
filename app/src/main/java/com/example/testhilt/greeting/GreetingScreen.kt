package com.example.testhilt.greeting

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun GreetingScreen(navController: NavController) {
    Button(onClick = { navController.navigate("todos") }) {
        Text(text = "Go to Todos")
    }
}