package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(savedInstanceState: Bundle?) called")
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

//        savedInstanceState?.apply {
//            buttonsActive = getBoolean("areButtonsActive")
//        }

        binding.trueButton.setOnClickListener { view ->
            checkAnswer(true, view)
        }

        binding.falseButton.setOnClickListener { view ->
            checkAnswer(false, view)
        }

        binding.nextButton.setOnClickListener { view ->
            quizViewModel.moveToNext()
            updateQuestion()
            areButtonsActive(true)
        }

        areButtonsActive(quizViewModel.buttonsActive)
        updateQuestion()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.apply {
//            putBoolean("areButtonsActive", buttonsActive)
//        }
//    }

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

    private fun areButtonsActive(isActive: Boolean) {
        quizViewModel.buttonsActive = isActive
        binding.trueButton.isEnabled = isActive
        binding.falseButton.isEnabled = isActive
    }

    private fun updateQuestion() {
        val questionTextResId =
            quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        val correctAnswer =
            quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.answerIsCorrect()
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()

        areButtonsActive(false)

        if (quizViewModel.currentIndex == quizViewModel.questionBank.lastIndex) {
            val record = quizViewModel.calcRecord()

            Snackbar.make(
                view,
                record,
                Snackbar.LENGTH_SHORT
            ).show()

            quizViewModel.resetScore()
        }
    }
}
