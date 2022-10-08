package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var currentScore = 0.0
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(savedInstanceState: Bundle?) called")
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.apply {
            currentIndex = getInt("currentIndex")
            binding.trueButton.isEnabled = getBoolean("isTrueBtnEnabled")
            binding.falseButton.isEnabled = getBoolean("isFalseBtnEnabled")
        }

        binding.trueButton.setOnClickListener { view ->
            checkAnswer(true, view)
        }

        binding.falseButton.setOnClickListener { view ->
            checkAnswer(false, view)
        }

        binding.nextButton.setOnClickListener { view ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentIndex)
        outState.putBoolean("isTrueBtnEnabled", binding.trueButton.isEnabled)
        outState.putBoolean("isFalseBtnEnabled", binding.falseButton.isEnabled)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }


    private fun updateQuestion() {
        val questionTextResId =
            questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        val correctAnswer =
            questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            currentScore++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()

        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

        if (currentIndex == questionBank.lastIndex) {
            val record = (currentScore / questionBank.size * 100).toInt().let { percentage ->
                val csInt = currentScore.toInt()
                "True: $csInt / False: ${questionBank.size - csInt} / Percentage: $percentage%"
            }
            Snackbar.make(
                view,
                record,
                Snackbar.LENGTH_SHORT
            ).show()

            currentScore = 0.0
        }
    }
}
