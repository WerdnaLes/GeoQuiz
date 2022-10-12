package com.example.geoquiz.challenges

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.Question
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityChallenge2Binding


class Challenge2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityChallenge2Binding


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityChallenge2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view ->
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener { view ->
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener { view ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.prevButton.setOnClickListener { view ->
            currentIndex = when {
                currentIndex - 1 < 0 -> questionBank.lastIndex
                else -> currentIndex - 1
            }
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener { view ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId =
            questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer =
            questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }
}
