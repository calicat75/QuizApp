package com.example.dailyquiz.domain.repository

import com.example.dailyquiz.domain.model.Question
import com.example.dailyquiz.domain.model.QuizResult

interface QuizRepository {
    suspend fun getQuestions(): List<Question>
    suspend fun getHistory(): List<QuizResult>
    suspend fun saveResult(result: QuizResult)
}