package com.example.mykamil

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    private val questions = arrayOf(
        Question("Какая столица Франции?",
            arrayOf("Берлин", "Москва", "Париж", "Лондон"), 2,null),
        Question("Сколько будет 2 + 2?",
            arrayOf("3", "4", "5", "6"), 1,null),
        Question("Какой цвет получается, если смешать красный и белый?",
            arrayOf("Розовый", "Зеленый", "Коричневый", "Оранжевый"), 0,null),
        Question("What is the picture of?",
            arrayOf("Option A", "Option B", "Option C", "Option D"), 2, R.drawable.img),
                Question("What is the picture of?",
        arrayOf("Option A", "Option B", "Option C", "Option D"), 2, R.drawable.img_1)

    )

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var textQuestion: TextView
    private lateinit var buttonNext: View
    private lateinit var textScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        textQuestion = findViewById(R.id.textQuestion)
        buttonNext = findViewById(R.id.buttonNext)
        textScore = findViewById(R.id.textScore)

        setupQuestion()

        findViewById<LinearLayout>(R.id.layoutAnswer1).setOnClickListener { checkAnswer(0) } // Option 1
        findViewById<LinearLayout>(R.id.layoutAnswer2).setOnClickListener { checkAnswer(1) } // Option 2
        findViewById<LinearLayout>(R.id.layoutAnswer3).setOnClickListener { checkAnswer(2) } // Option 3
        findViewById<LinearLayout>(R.id.layoutAnswer4).setOnClickListener { checkAnswer(3) } // Option 4
        findViewById<LinearLayout>(R.id.layoutAnswer4).setOnClickListener { checkAnswer(4) } // Option 5

        buttonNext.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                setupQuestion()
            } else {
                showFinalScore()
            }
        }
    }

    private fun setupQuestion() {
        buttonNext.visibility = View.GONE
        val question = questions[currentQuestionIndex]
        textQuestion.text = question.text

        findViewById<TextView>(R.id.textOption1).text = question.options[0]
        findViewById<TextView>(R.id.textOption2).text = question.options[1]
        findViewById<TextView>(R.id.textOption3).text = question.options[2]
        findViewById<TextView>(R.id.textOption4).text = question.options[3]

        val imageQuestion: ImageView = findViewById(R.id.imageQuestion)
        if (question.imageResId != null) {
            imageQuestion.setImageResource(question.imageResId)
            imageQuestion.visibility = View.VISIBLE
        } else {
            imageQuestion.visibility = View.GONE
        }
    }


    private fun checkAnswer(selectedOption: Int) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer
        if (selectedOption == correctAnswer) {
            score++
            Toast.makeText(this, "Correct! 🎉", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong! ❌", Toast.LENGTH_SHORT).show()
        }
        buttonNext.visibility = View.VISIBLE
        textScore.text = "Score: $score"
    }

    private fun showFinalScore() {
        Toast.makeText(this, "Quiz Finished! Your score is $score", Toast.LENGTH_LONG).show()
        resetQuiz()
    }

    private fun resetQuiz() {
        currentQuestionIndex = 0
        score = 0
        textScore.text = "Score: 0"
        setupQuestion()
    }

    data class Question(val text: String, val options: Array<String>, val correctAnswer: Int, val imageResId: Int?)

}
