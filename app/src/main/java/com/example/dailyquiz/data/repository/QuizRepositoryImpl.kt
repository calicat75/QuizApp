package com.example.dailyquiz.data.repository

import com.example.dailyquiz.data.remote.QuizAPI
import com.example.dailyquiz.domain.model.Question
import com.example.dailyquiz.domain.model.QuizResult
import com.example.dailyquiz.domain.repository.QuizRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val quizApi: QuizAPI
) : QuizRepository {

    private val history = mutableListOf<QuizResult>()

    override suspend fun getQuestions(category: String, difficulty: String, type: String    ): List<Question> {
        val response = quizApi.getQuestions(
            amount = 5,
            category = category,
            difficulty = difficulty,
            type = type)
        return response.results.mapIndexed { index, dto ->
            val options = (dto.incorrect_answers + dto.correct_answer).shuffled()
            Question(
                id = index.toString(),
                text = dto.question,
                options = options,
                correctIndex = options.indexOf(dto.correct_answer),
                category = dto.category,
                difficulty = dto.difficulty
            )
        }
    }

    override suspend fun getHistory(): List<QuizResult> {
        return history.reversed()
    }

    override suspend fun saveResult(result: QuizResult) {
        history.add(result)
    }
}