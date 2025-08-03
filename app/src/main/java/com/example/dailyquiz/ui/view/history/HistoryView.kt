package com.example.dailyquiz.ui.view.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dailyquiz.ui.viewmodel.HistoryViewModel
import java.time.format.DateTimeFormatter

@Composable
fun HistoryView(
    historyViewModel: HistoryViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val historyList = historyViewModel.historyItems.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    LaunchedEffect(Unit) {
        historyViewModel.loadHistory()
    }

    if (historyList.value.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(historyList.value) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val sessionId = historyList.value.indexOf(item)
                            navController.navigate("review/$sessionId")
                        },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Попытка от: ${item.timestamp.format(formatter)}")
                        Text("Результат: ${item.correctAnswers} из 5")
                        item.category?.let {
                            Text("Категория: $it")
                        }
                        item.difficulty?.let {
                            Text("Сложность: $it")
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Вы еще не проходили ни одной викторины",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}