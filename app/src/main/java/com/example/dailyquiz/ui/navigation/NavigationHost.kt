package com.example.dailyquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailyquiz.ui.view.quiz.QuizView
import com.example.dailyquiz.ui.view.history.HistoryView
import com.example.dailyquiz.ui.view.review.ReviewView
import com.example.dailyquiz.ui.viewmodel.HistoryViewModel
import com.example.dailyquiz.ui.viewmodel.QuizViewModel
import com.example.dailyquiz.ui.viewmodel.ReviewViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val quizViewModel: QuizViewModel = hiltViewModel()
    val historyViewModel: HistoryViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Quiz.route,
        modifier = modifier
    ) {
        composable(Screen.Quiz.route) {
            QuizView(navController = navController, viewModel = quizViewModel)
        }

        composable(Screen.History.route) {
            HistoryView(navController = navController, viewModel = historyViewModel)
        }

        composable("${Screen.Review.route}/{sessionId}") { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId")?.toIntOrNull()
            val reviewViewModel: ReviewViewModel = hiltViewModel()
            sessionId?.let {
                ReviewView(sessionId = it, viewModel = reviewViewModel)
            }
        }
    }
}