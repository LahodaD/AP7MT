package com.example.lahodaprojekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import com.example.lahodaprojekt.databinding.ActivityQuizStatisticBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class QuizStatistic : AppCompatActivity() {

    private lateinit var binding: ActivityQuizStatisticBinding

    private var oneLine: String? = ""
    private var textView: TextView? = null

    private val TAG = "MMMMM"
    val appFileName = "appFileProject.txt"
    val appDirName = "/appDir/"
    val appDirPath = Environment.getExternalStorageDirectory().absolutePath + appDirName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textView = binding.scoreText
        val backBtn = binding.backBtnStat
        readFromExternalMemory()
        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun readFromExternalMemory(): Boolean {
        return try {
            val appFile = File(appDirPath + appFileName)
            if (appFile.exists()) {
                val fis = FileInputStream(File(appDirPath + appFileName))
                val isr = InputStreamReader(fis)
                val bufferedReader = BufferedReader(isr)
                var readResult = ""

                while (bufferedReader.readLine().also { oneLine = it } != null) {
                    readResult += oneLine
                    readResult += "\n"
                }
//                oneLine = bufferedReader.readLine()
//                while (oneLine != null) {
//                    readResult += oneLine
//                    readResult += "\n"
//                    oneLine = bufferedReader.readLine()
//                }

                bufferedReader.close()
                isr.close()
                fis.close()
                textView!!.text = readResult
            }
            true
        } catch (e: Exception) {
            textView!!.text = "Chyba cteni"
            Log.d(TAG, "Chyba cteni \n" + e.toString());
            false
        }
    }
}