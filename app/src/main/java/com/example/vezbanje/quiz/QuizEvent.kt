package com.example.vezbanje.quiz

sealed class QuizEvent {
    data class ShareScore(val nickname: String, val score: Float) : QuizEvent()
}