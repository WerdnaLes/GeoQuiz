package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CURRENT_SCORE_KEY = "CURRENT_SCORE_KEY"
const val BUTTONS_ACTIVE_KEY = "BUTTONS_ACTIVE_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val CHEATED_ANSWERS = "CHEATED_ANSWERS"
const val CHEAT_COUNTER_KEY = "CHEAT_COUNTER_KEY"

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

    var isCheater: Boolean
        get() = savedStateHandle[IS_CHEATER_KEY] ?: false
        set(value) =
            savedStateHandle.set(IS_CHEATER_KEY, value)

    var cheatedAnswers: Int
        get() = savedStateHandle[CHEATED_ANSWERS] ?: 0
        set(value) = savedStateHandle.set(CHEATED_ANSWERS, value)

    var cheatCounter: Int
        get() = savedStateHandle[CHEAT_COUNTER_KEY] ?: 3
        set(value) =
            savedStateHandle.set(CHEAT_COUNTER_KEY, value)

    private var currentScore: Double
        get() = savedStateHandle[CURRENT_SCORE_KEY] ?: 0.0
        set(value) =
            savedStateHandle.set(CURRENT_SCORE_KEY, value)

    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        private set(value) =
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
            val trueAnswers = currentScore.toInt()
            val falseAnswers = ((questionBank.size - trueAnswers) - cheatedAnswers).let {
                if (it < 0) 0 else it
            }
            "True: $trueAnswers / False: $falseAnswers / Cheated: $cheatedAnswers Percentage: $percentage%"
        }

    fun cheatedAnswer() {
        cheatedAnswers++
        cheatCounter--
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun resetScore() {
        currentScore = 0.0
        cheatedAnswers = 0
    }

    fun answerIsCorrect() {
        currentScore++
    }
}