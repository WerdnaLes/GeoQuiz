package com.example.geoquiz.challenges

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.R
import com.google.android.material.snackbar.Snackbar

class Challenge1Activity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chall1)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view ->
            Snackbar.make(
                /* view = */ view,
                /* resId = */ R.string.correct_toast,
                /* duration = */ Snackbar.LENGTH_SHORT
            ).show()
        }

        falseButton.setOnClickListener { view ->
            Snackbar.make(
                /* view = */ view,
                /* resId = */ R.string.incorrect_toast,
                /* duration = */ Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}
