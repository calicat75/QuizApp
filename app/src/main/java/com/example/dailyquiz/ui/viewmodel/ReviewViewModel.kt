package com.example.dailyquiz.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.dailyquiz.domain.model.QuizResult
import com.example.dailyquiz.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    fun getResultById(id: Int): QuizResult? {
        return runCatching { quizRepository.getHistory()[id] }.getOrNull()
    }
}