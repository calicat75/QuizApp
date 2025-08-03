package com.example.dailyquiz.domain.usecase

import com.example.dailyquiz.data.common.Resource
import com.example.dailyquiz.domain.model.Question
import com.example.dailyquiz.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(): Flow<Resource<List<Question>>> = flow {
        emit(com.example.dailyquiz.data.common.Resource.Loading())
        try {
            val questions = quizRepository.getQuestions()
            emit(Resource.Success(questions))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}