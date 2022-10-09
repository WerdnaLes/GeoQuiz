package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CURRENT_SCORE_KEY = "CURRENT_SCORE_KEY"
const val BUTTONS_ACTIVE_KEY = "BUTTONS_ACTIVE_KEY"

class QuizViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    var currentScore: Double
        get() = savedStateHandle[CURRENT_SCORE_KEY] ?: 0.0
        set(value) =
            savedStateHandle.set(CURRENT_SCORE_KEY, value)

    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) =
            savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var buttonsActive: Boolean
        get() = savedStateHandle[BUTTONS_ACTIVE_KEY] ?: true
        set(value) =
            savedStateHandle.set(BUTTONS_ACTIVE_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun calcRecord() =
        (currentScore / questionBank.size * 100).toInt().let { percentage ->
            val csInt = currentScore.toInt()
            "True: $csInt / False: ${questionBank.size - csInt} / Percentage: $percentage%"
        }


    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun resetScore() {
        currentScore = 0.0
    }

    fun answerIsCorrect() {
        currentScore++
    }
}