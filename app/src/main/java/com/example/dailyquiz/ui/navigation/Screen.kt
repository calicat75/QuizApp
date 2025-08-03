package com.example.dailyquiz.ui.navigation

import androidx.annotation.DrawableRes
import com.example.dailyquiz.R


sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int? = null) {
    data object Quiz : Screen("quiz", "Викторина", R.drawable.ic_quiz)
    data object History : Screen("history", "История", R.drawable.ic_history)
    data object Review : Screen("review", "Разбор")

    companion object {
        val entries = listOf(Quiz, History)
    }
}
