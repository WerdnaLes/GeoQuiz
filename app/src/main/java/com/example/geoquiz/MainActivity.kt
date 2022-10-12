package com.example.geoquiz

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(savedInstanceState: Bundle?) called")
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
        val cheatText2 = "You have ${quizViewModel.cheatCounter} cheat tokens left"

        binding.trueButton.setOnClickListener { view ->
            checkAnswer(true, view)
        }

        binding.falseButton.setOnClickListener { view ->
            checkAnswer(false, view)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            areButtonsActive(true)
        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent =
                CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        binding.showCheatsRemainingTextView.text = cheatText2

        areButtonsActive(quizViewModel.buttonsActive)
        updateQuestion()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurCheatButton()
        }
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

        val messageResId = when {
            quizViewModel.isCheater -> {
                cheatingManagement()
                R.string.judgement_toast
            }
            userAnswer == correctAnswer -> {
                quizViewModel.answerIsCorrect()
                R.string.correct_toast
            }
            else -> R.string.incorrect_toast
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

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton() {
        val effect = RenderEffect.createBlurEffect(
            10.0f,
            10.0f,
            Shader.TileMode.CLAMP
        )
        binding.cheatButton.setRenderEffect(effect)
    }

    private fun cheatingManagement() {
        quizViewModel.cheatedAnswer()
        quizViewModel.isCheater = false
        val cheatText = when {
            quizViewModel.cheatCounter > 0 -> "You have ${quizViewModel.cheatCounter} cheat tokens left"
            else -> {
                binding.cheatButton.isEnabled = false
                "You can't cheat anymore!"
            }
        }
        binding.showCheatsRemainingTextView.text = cheatText
    }
}
