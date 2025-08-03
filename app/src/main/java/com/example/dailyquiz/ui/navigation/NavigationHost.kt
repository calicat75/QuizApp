package com.example.dailyquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dailyquiz.data.common.Constants.type
import com.example.dailyquiz.ui.view.quiz.QuizView
import com.example.dailyquiz.ui.view.history.HistoryView
import com.example.dailyquiz.ui.view.quiz.FirstScreen
import com.example.dailyquiz.ui.view.review.ReviewView
import com.example.dailyquiz.ui.view.selection.QuizSelectionScreen
import com.example.dailyquiz.ui.viewmodel.HistoryViewModel
import com.example.dailyquiz.ui.viewmodel.QuizViewModel
import com.example.dailyquiz.ui.viewmodel.ReviewViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val quizViewModel: QuizViewModel = hiltViewModel()
    val historyViewModel: HistoryViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Selection.route,
        modifier = modifier
    ) {
        composable(
            route = Screen.First.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "General Knowledge"
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "Easy"
            val type = backStackEntry.arguments?.getString("type") ?: "Multiple Choice"

            FirstScreen(
                navController = navController,
                quizCategory = category,
                quizDifficulty = difficulty,
                quizType = type,
                viewModel = quizViewModel
            )
        }

        composable(Screen.Quiz.route) {
            QuizView(navController = navController, viewModel = quizViewModel)
        }

        composable(Screen.History.route) {
            HistoryView(navController = navController, historyViewModel = historyViewModel)
        }

        composable(
            route = Screen.Review.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getInt("sessionId")
            val reviewViewModel: ReviewViewModel = hiltViewModel()
            sessionId?.let {
                ReviewView(sessionId = it, viewModel = reviewViewModel)
            }
        }
        composable(Screen.Selection.route) {
            QuizSelectionScreen(navController)
        }

    }
}