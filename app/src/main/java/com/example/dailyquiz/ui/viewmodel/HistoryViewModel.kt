package com.example.dailyquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyquiz.domain.model.QuizResult
import com.example.dailyquiz.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    private val _historyItems = MutableStateFlow<List<QuizResult>>(emptyList())
    val historyItems: StateFlow<List<QuizResult>> = _historyItems

    private val _selectedResult = MutableStateFlow<QuizResult?>(null)
    val selectedResult: StateFlow<QuizResult?> = _selectedResult

    fun loadHistory() {
        viewModelScope.launch {
            _historyItems.value = repository.getHistory()
        }
    }

    fun onHistoryItemClicked(item: QuizResult) {
        _selectedResult.value = item
    }
}