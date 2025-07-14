package com.example.vezbanje.quiz

import com.example.vezbanje.quiz.model.QuizQuestion

data class QuizQuestionState(
    val loading: Boolean = false,
    val items: List<QuizQuestion>? = null,
    val error: Throwable? = null,
)