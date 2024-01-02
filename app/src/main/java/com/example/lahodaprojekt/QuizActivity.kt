package com.example.lahodaprojekt

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.lahodaprojekt.databinding.ActivityQuizBinding
import java.util.Random
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.scheduleAtFixedRate

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    private var quizTimer: Timer? = null
    private var totalTimeInMins = 1
    private var seconds = 0

    private var correctAnswers = 0

    private lateinit var question: TextView
    private lateinit var option1: AppCompatButton
    private lateinit var option2: AppCompatButton
    private lateinit var option3: AppCompatButton
    private lateinit var option4: AppCompatButton
    private lateinit var nextBtn: AppCompatButton

    private var getQuestionListList: ArrayList<QuestionList>? = null
    private var currentQuestionPosition = 0
    private var selectedOptionByUser = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuestionListList = intent.getParcelableArrayListExtra("LIST")

        val backBtn = binding.backBtn
        val timer = binding.timerText

        question = binding.question
        option1 = binding.option1
        option2 = binding.option2
        option3 = binding.option3
        option4 = binding.option4

        question.setTextColor(Color.WHITE)
        option1.setTextColor(Color.WHITE)
        option2.setTextColor(Color.WHITE)
        option3.setTextColor(Color.WHITE)
        option4.setTextColor(Color.WHITE)

        nextBtn = binding.next

        startTimer(timer)
        question.setText(getQuestionListList!![currentQuestionPosition].question)
        randomShaker()
        option1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option1.getText().toString()
                    option1.setBackgroundResource(R.drawable.round_back_red)
                    option1.setTextColor(Color.WHITE)
                    isCorrect()
                }
            }
        })

        option2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option2.getText().toString()
                    option2.setBackgroundResource(R.drawable.round_back_red)
                    option2.setTextColor(Color.WHITE)
                    isCorrect()
                }
            }
        })
        option3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option3.getText().toString()
                    option3.setBackgroundResource(R.drawable.round_back_red)
                    option3.setTextColor(Color.WHITE)
                    isCorrect()
                }
            }
        })

        option4.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (selectedOptionByUser.isEmpty()) {
                    selectedOptionByUser = option4.getText().toString()
                    option4.setBackgroundResource(R.drawable.round_back_red)
                    option4.setTextColor(Color.WHITE)
                    isCorrect()
                }
            }
        })

        nextBtn.setOnClickListener(View.OnClickListener {
            if (selectedOptionByUser.isEmpty()) {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show()
            } else {
                changeNextQuestion()
            }
        })

        backBtn.setOnClickListener {
            quizTimer!!.purge()
            quizTimer!!.cancel()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun changeNextQuestion() {
        currentQuestionPosition++
        if (currentQuestionPosition + 1 == getQuestionListList!!.size) {
            nextBtn!!.text = "Submit Quiz"
        }
        if (currentQuestionPosition < getQuestionListList!!.size) {
            selectedOptionByUser = ""
            option1!!.setBackgroundResource(R.drawable.round_back_light_light_purple)
            option2!!.setBackgroundResource(R.drawable.round_back_light_light_purple)
            option3!!.setBackgroundResource(R.drawable.round_back_light_light_purple)
            option4!!.setBackgroundResource(R.drawable.round_back_light_light_purple)
            randomShaker()
        } else {
            quizTimer!!.purge()
            quizTimer!!.cancel()
            val intent = Intent(this, QuizResults::class.java)
            intent.putExtra("correct", correctAnswers)
            startActivity(intent)
            finish()
        }
    }

    private fun startTimer(timerTextView: TextView) {
        quizTimer = Timer()
        quizTimer!!.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (seconds == 0 && totalTimeInMins == 0) {

                    //Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();
                    quizTimer!!.purge()
                    quizTimer!!.cancel()

                    val timeOver = true
                    val intent = Intent(this@QuizActivity, QuizResults::class.java)

                    intent.putExtra("timeOver", timeOver)
                    intent.putExtra("correct", correctAnswers)
                    startActivity(intent)
                    finish()
                } else if (seconds == 0) {
                    totalTimeInMins--
                    seconds = 59
                } else {
                    seconds--
                }
                runOnUiThread {
                    var finalMinutes = totalTimeInMins.toString()
                    var finalSeconds = seconds.toString()
                    if (finalMinutes.length == 1) {
                        finalMinutes = "0$finalMinutes"
                    }
                    if (finalSeconds.length == 1) {
                        finalSeconds = "0$finalSeconds"
                    }
                    timerTextView.text = "$finalMinutes:$finalSeconds"
                }
            }
        }, 1000, 1000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        quizTimer!!.purge()
        quizTimer!!.cancel()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun randomShaker() {
        val random = Random()
        val x = random.nextInt(4)
        question!!.text = getQuestionListList!![currentQuestionPosition].question
        when (x) {
            1 -> {
                option1!!.text = getQuestionListList!![currentQuestionPosition].correctAnswer
                option2!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[0]
                option3!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[1]
                option4!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[2]
            }

            2 -> {
                option4!!.text = getQuestionListList!![currentQuestionPosition].correctAnswer
                option1!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[0]
                option2!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[1]
                option3!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[2]
            }

            3 -> {
                option3!!.text = getQuestionListList!![currentQuestionPosition].correctAnswer
                option4!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[0]
                option1!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[1]
                option2!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[2]
            }

            0 -> {
                option2!!.text = getQuestionListList!![currentQuestionPosition].correctAnswer
                option3!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[0]
                option4!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[1]
                option1!!.text = getQuestionListList!![currentQuestionPosition].incorrectAnswers[2]
            }
        }
    }

    private fun isCorrect() {
        val getAnswer = getQuestionListList!![currentQuestionPosition].correctAnswer
        if (option1!!.text.toString() == getAnswer) {
            option1!!.setBackgroundResource(R.drawable.round_back_green)
            option1!!.setTextColor(Color.WHITE)
        } else if (option2!!.text.toString() == getAnswer) {
            option2!!.setBackgroundResource(R.drawable.round_back_green)
            option2!!.setTextColor(Color.WHITE)
        } else if (option3!!.text.toString() == getAnswer) {
            option3!!.setBackgroundResource(R.drawable.round_back_green)
            option3!!.setTextColor(Color.WHITE)
        } else if (option4!!.text.toString() == getAnswer) {
            option4!!.setBackgroundResource(R.drawable.round_back_green)
            option4!!.setTextColor(Color.WHITE)
        }
        if (selectedOptionByUser == getAnswer) {
            correctAnswers++
        }
    }
}