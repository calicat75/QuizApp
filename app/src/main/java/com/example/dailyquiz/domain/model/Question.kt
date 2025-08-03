package com.example.dailyquiz.domain.model

data class Question(
    val id: String,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    var selectedIndex: Int? = null,
    val category: String? = null,
    val difficulty: String? = null
)