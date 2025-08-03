package com.example.dailyquiz.ui.navigation

import androidx.annotation.DrawableRes
import com.example.dailyquiz.R


sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int = 0) {
    data object Quiz : Screen("quiz", "Викторина", R.drawable.ic_quiz)
    data object History : Screen("history", "История", R.drawable.ic_history)
    data object Review : Screen("review", "Разбор")
    data object First : Screen("first", "Первый экран")


    companion object {
        val entries = listOf(Quiz, History)
    }
}
