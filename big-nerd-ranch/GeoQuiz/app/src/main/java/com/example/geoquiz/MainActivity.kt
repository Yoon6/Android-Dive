package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var correctQuestion = BooleanArray(questionBank.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        previousButton = findViewById(R.id.previous_image_button)
        nextButton = findViewById(R.id.next_image_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        previousButton.setOnClickListener {
            currentIndex = if (currentIndex <= 0) {
                questionBank.size - 1
            } else {
                currentIndex - 1
            }
            updateQuestion()
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val message = when (userAnswer == correctAnswer) {
            true -> {
                correctQuestion[currentIndex] = true
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
                R.string.correct_toast
            }
            false -> R.string.incorrect_toast
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        if (correctQuestion[currentIndex]) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
    }
}