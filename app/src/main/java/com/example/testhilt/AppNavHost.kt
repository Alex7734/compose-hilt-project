package com.example.testhilt

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testhilt.greetings.GreetingScreen
import com.example.testhilt.todos.TodoScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "greeting",
        modifier = modifier
    ) {
        composable("greeting") {
            GreetingScreen(navController = navController)
        }
        composable("todos") {
            TodoScreen()
        }
    }
}