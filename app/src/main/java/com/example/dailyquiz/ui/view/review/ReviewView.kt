package com.example.dailyquiz.ui.view.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dailyquiz.ui.viewmodel.ReviewViewModel

@Composable
fun ReviewView(sessionId: Int, viewModel: ReviewViewModel, modifier: Modifier = Modifier) {
    val result by viewModel.quizResult.collectAsState()

    LaunchedEffect(sessionId) {
        viewModel.loadResultById(sessionId)
    }

    result?.let {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            itemsIndexed(it.questions) { index, q ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Вопрос ${index + 1}: ${q.text}")
                        q.options.forEachIndexed { i, ans ->
                            val color = when {
                                i == q.correctIndex -> Color.Green
                                i == q.selectedIndex && q.selectedIndex != q.correctIndex -> Color.Red
                                else -> Color.Unspecified
                            }
                            Text(text = ans, color = color)
                        }
                    }
                }
            }
        }
    } ?: run {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Результат не найден")
        }
    }
}