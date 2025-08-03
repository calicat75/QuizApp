package com.example.dailyquiz.domain.model

import java.time.LocalDateTime

data class QuizResult(
    val questions: List<Question>,
    val correctAnswers: Int,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val category: String? = null,
    val difficulty: String? = null
)