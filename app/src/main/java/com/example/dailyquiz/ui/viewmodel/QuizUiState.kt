package com.example.dailyquiz.ui.viewmodel

import com.example.dailyquiz.domain.model.Question

sealed class QuizUiState {
    data object Start : QuizUiState()
    data object Loading : QuizUiState()
    data class QuestionState(
        val questions: List<Question>,
        val currentIndex: Int
    ) : QuizUiState()
    data class Result(
        val correctAnswers: Int,
        val total: Int,
        val questions: List<Question>
    ) : QuizUiState()
}