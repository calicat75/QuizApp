package com.example.dailyquiz.ui.view.quiz

import com.example.dailyquiz.ui.viewmodel.QuizUiState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dailyquiz.ui.viewmodel.QuizViewModel

@Composable
fun QuizView(navController: NavHostController, viewModel: QuizViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = viewModel.uiState.collectAsState().value) {
            is QuizUiState.Start -> StartQuizView(viewModel)
            is QuizUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is QuizUiState.QuestionState -> QuestionView(viewModel, state)
            is QuizUiState.Result -> ResultView(viewModel, state)
        }
    }
}

@Composable
fun StartQuizView(viewModel: QuizViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Добро пожаловать в DailyQuiz!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.startQuiz() }) {
            Text("Начать викторину")
        }
    }
}

@Composable
fun QuestionView(viewModel: QuizViewModel, state: QuizUiState.QuestionState) {
    val currentQuestion = state.questions[state.currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Вопрос ${state.currentIndex + 1} из ${state.questions.size}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(currentQuestion.text)
        Spacer(modifier = Modifier.height(16.dp))

        currentQuestion.options.forEachIndexed { index, answer ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = currentQuestion.selectedIndex == index,
                    onClick = { viewModel.selectAnswer(index) }
                )
                Text(answer)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.submitAnswer() },
            enabled = currentQuestion.selectedIndex != null,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(if (state.currentIndex == state.questions.lastIndex) "Завершить" else "Далее")
        }
    }
}

@Composable
fun ResultView(viewModel: QuizViewModel, state: QuizUiState.Result) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Результат", style = MaterialTheme.typography.headlineSmall)
        Text("Вы ответили правильно на ${state.correctAnswers} из ${state.total}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.restart() }) {
            Text("Начать заново")
        }
    }
}