package com.example.geoquiz.challenges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.geoquiz.R

class Challenge1Activity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chall1)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view ->
            Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).show()
        }

        falseButton.setOnClickListener { view ->
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}
