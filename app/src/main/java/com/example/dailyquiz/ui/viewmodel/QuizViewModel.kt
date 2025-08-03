package com.example.dailyquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiz.domain.model.Question
import com.example.dailyquiz.domain.model.QuizResult
import com.example.dailyquiz.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Start)
    val uiState: StateFlow<QuizUiState> = _uiState

    private var currentIndex = 0
    private var questions: List<Question> = emptyList()
    private val selectedAnswers = mutableListOf<Int?>()

    fun startQuiz(category: String, difficulty: String, type: String) {
        _uiState.value = QuizUiState.Loading
        viewModelScope.launch {
            try {
                questions = quizRepository.getQuestions(category, difficulty, type)
                if (questions.isEmpty()) {
                    _uiState.value = QuizUiState.Error("Вопросы не найдены")
                } else {
                    selectedAnswers.clear()
                    selectedAnswers.addAll(List(questions.size) { null })
                    currentIndex = 0
                    showCurrentQuestion()
                }
            } catch (e: Exception) {
                _uiState.value = QuizUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun selectAnswer(index: Int) {
        if (selectedAnswers.indices.contains(currentIndex)) {
            selectedAnswers[currentIndex] = index
        }
    }

    fun submitAnswer() {
        if (currentIndex < questions.lastIndex) {
            currentIndex++
            showCurrentQuestion()
        } else {
            finishQuiz()
        }
    }

    private fun showCurrentQuestion() {
        _uiState.value = QuizUiState.QuestionState(
            questions = questions,
            currentIndex = currentIndex
        )
    }

    private fun finishQuiz() {
        val correctAnswers = questions.zip(selectedAnswers).count { (q, selected) -> q.correctIndex == selected }

        val result = QuizResult(
            questions = questions.mapIndexed { i, q -> q.copy(selectedIndex = selectedAnswers[i]) },
            correctAnswers = correctAnswers
        )

        viewModelScope.launch {
            quizRepository.saveResult(result)
            _uiState.value = QuizUiState.Result(
                correctAnswers = correctAnswers,
                total = questions.size,
                questions = result.questions
            )
        }
    }

    fun restart() {
        _uiState.value = QuizUiState.Start
    }
}