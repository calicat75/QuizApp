package com.example.dailyquiz.ui.view.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dailyquiz.R
import com.example.dailyquiz.data.common.Constants
import com.example.dailyquiz.ui.viewmodel.QuizViewModel
import com.example.dailyquiz.ui.navigation.Screen
import com.example.dailyquiz.ui.viewmodel.QuizUiState

@Composable
fun FirstScreen(
    navController: NavController,
    quizCategory: String,
    quizDifficulty: String,
    quizType: String,
    viewModel: QuizViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    // Назад — выход на экран выбора викторины
    BackHandler {
        navController.navigate(Screen.Quiz.route) {
            popUpTo(Screen.Quiz.route) { inclusive = true }
        }
    }

    // Запускаем викторину при первом входе
    LaunchedEffect(Unit) {
        viewModel.startQuiz(
            category = quizCategory,
            difficulty = quizDifficulty,
            type = quizType
        )
    }

    when (uiState) {
        is QuizUiState.Start -> {
            Text("Подготовка викторины...")
        }

        is QuizUiState.Loading -> {
            Text("Загрузка...")
        }

        is QuizUiState.QuestionState -> {
            val state = uiState as QuizUiState.QuestionState
            val question = state.questions[state.currentIndex]

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Вопрос ${state.currentIndex + 1} / ${state.questions.size}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = question.text)

                Spacer(modifier = Modifier.height(16.dp))

                question.options.forEachIndexed { index, option ->
                    Button(
                        onClick = { viewModel.selectAnswer(index) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = option)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.submitAnswer() }
                ) {
                    Text(if (state.currentIndex == state.questions.lastIndex) "Завершить" else "Далее")
                }
            }
        }

        is QuizUiState.Result -> {
            val result = uiState as QuizUiState.Result
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Викторина завершена!")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Правильных ответов: ${result.correctAnswers} из ${result.total}")

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.restart() }) {
                    Text("Начать заново")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    navController.navigate(Screen.Quiz.route) {
                        popUpTo(Screen.Quiz.route) { inclusive = true }
                    }
                }) {
                    Text("На главный экран")
                }
            }
        }
        is QuizUiState.Error -> {
            Text("Ошибка: ${(uiState as QuizUiState.Error).message}")
        }
    }
}