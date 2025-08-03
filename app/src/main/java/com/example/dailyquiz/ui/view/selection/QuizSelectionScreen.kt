package com.example.dailyquiz.ui.view.selection

import androidx.annotation.DrawableRes
import com.example.dailyquiz.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Selection : Screen(
        route = "selection_screen",
        title = "Выбор",
        icon = R.drawable.ic_selection
    )

    object Quiz : Screen(
        route = "quiz",
        title = "Викторина",
        icon = R.drawable.ic_quiz
    )

    object History : Screen(
        route = "history",
        title = "История",
        icon = R.drawable.ic_history
    )

    object First : Screen(
        route = "first/{category}/{difficulty}/{type}",
        title = "Старт",
        icon = R.drawable.ic_quiz
    ) {
        fun createRoute(category: String, difficulty: String, type: String): String {
            return "first/$category/$difficulty/$type"
        }
    }

    object Review : Screen(
        route = "review_screen/{sessionId}",
        title = "Обзор",
        icon = R.drawable.ic_history
    ) {
        fun createRoute(sessionId: Int): String {
            return "review_screen/$sessionId"
        }
    }
}