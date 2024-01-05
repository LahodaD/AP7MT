package com.example.lahodaprojekt

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lahodaprojekt.databinding.ActivityQuizResultsBinding
import java.io.File
import java.io.FileOutputStream

class QuizResults : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultsBinding
    private lateinit var correctAnsw: TextView
    private lateinit var editText: EditText
    private lateinit var saveButton: Button

    private var timeOver = false
    private var correct = 0

    val SHARED_PREFS = "sharedPrefs"
    val TEXT = "text"
    val SCORE = "score"
    val appFileName = "appFileProject.txt"
    val appDirName = "/appDir/"
    val appDirPath = Environment.getExternalStorageDirectory().absolutePath + appDirName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editText = binding.editText
        saveButton = binding.saveBtn

        val backBtn = binding.backBtnResult
        correct = intent.getIntExtra("correct", 0)
        timeOver = intent.getBooleanExtra("timeOver", false)

        correctAnsw = binding.correctAnsw
        correctAnsw.setText("Correct Answer : $correct")
        if (timeOver) {
            val congra = binding.congratulation
            congra.text = "TIME OVER"
            val textMessage = binding.textMessage
            textMessage.text = "Time is up, the Quiz has ended"
        }

        saveButton.setOnClickListener {
            writeToExternalMemory(
                editText.text.toString() + " : score - " + correct + "\n"
            )
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun writeToExternalMemory(dataToWrite: String): Boolean {
        return try {
            val appDir = File(appDirPath)
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val fos = FileOutputStream(appDirPath + appFileName, true)
            fos.write(dataToWrite.toByteArray())
            fos.flush()
            fos.close()
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "ZAPIS JE OK.");
            true
        } catch (e: Exception) {
            Log.d(TAG, "Chyba zapisu: " + e.toString());
            false
        }
    }
}