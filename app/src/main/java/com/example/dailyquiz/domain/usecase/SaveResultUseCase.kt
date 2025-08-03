package com.example.dailyquiz.domain.usecase

import com.example.dailyquiz.domain.model.QuizResult
import com.example.dailyquiz.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveResultUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    suspend operator fun invoke(result: QuizResult) = withContext(Dispatchers.IO) {
        quizRepository.saveResult(result)
    }
}