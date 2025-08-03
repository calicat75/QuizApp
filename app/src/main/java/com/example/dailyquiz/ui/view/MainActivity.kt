package com.example.dailyquiz.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.example.dailyquiz.ui.navigation.BottomNavigationBar
import com.example.dailyquiz.ui.navigation.NavigationHost
import com.example.dailyquiz.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.dailyquiz.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = colorResource(id = R.color.mid_night_blue)),
                        contentAlignment = Alignment.Center
                    ) {
                        NavigationHost(navController = navController)
                    }
                }
            }
        }
    }
}