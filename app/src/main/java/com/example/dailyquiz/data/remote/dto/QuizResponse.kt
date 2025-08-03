package com.example.dailyquiz.data.remote.dto

data class QuizResponse(
    val results: List<QuestionDto>
)

data class QuestionDto(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>,
    val category: String?,
    val difficulty: String?
)