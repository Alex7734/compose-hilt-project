package com.example.testhilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.testhilt.ui.theme.TestHiltTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestHiltTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier
                            .padding(contentPadding)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

