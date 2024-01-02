package com.example.lahodaprojekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lahodaprojekt.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var questionListList: MutableList<QuestionList>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startNewQuiz = binding.newGame
        val statistic = binding.statistic
        val exit = binding.exit

        questionListList = ArrayList()
        getQuestions()

        startNewQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("LIST", questionListList as ArrayList<QuestionList>)
            startActivity(intent)
        }
//
//        statistic.setOnClickListener {
//            val intent = Intent(this, QuizStatistic::class.java)
//            startActivity(intent)
//        }

        exit.setOnClickListener {
            ActivityCompat.finishAffinity(this)
        }
    }

    private fun getQuestions() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://the-trivia-api.com/api/questions?categories=arts_and_literature,film_and_tv,food_and_drink,general_knowledge,geography,history,music,science,society_and_culture,sport_and_leisure&limit=10"

        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                val mJsonArray = JSONArray(response)

                for (i in 0 until mJsonArray.length()) {
                    val mJsonObject = mJsonArray.getJSONObject(i)

                    val questionList = QuestionList(
                        mJsonObject.getString("category"),
                        mJsonObject.getString("id"),
                        mJsonObject.getString("correctAnswer"),
                        getListPropertieAnsw(mJsonObject, "incorrectAnswers"),
                        mJsonObject.getString("question"),
                        getListPropertieAnsw(mJsonObject, "tags"),
                        mJsonObject.getString("type"),
                        mJsonObject.getString("difficulty"),
                        getListPropertieAnsw(mJsonObject, "regions"),
                        mJsonObject.getString("difficulty") == "true"
                    )
                    questionListList?.add(questionList)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error ->
            error.printStackTrace()
        })

        queue.add(request)
    }

    private fun getListPropertieAnsw(`object`: JSONObject, namePropertie: String): List<String> {
        val list: MutableList<String> = ArrayList()
        var mJsonArrayProperty: JSONArray? = null
        return try {
            mJsonArrayProperty = `object`.getJSONArray(namePropertie)
            for (i in 0 until mJsonArrayProperty.length()) {
                val mJsonObjectProperty = mJsonArrayProperty.getString(i)
                list.add(mJsonObjectProperty)
            }
            list
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
    }
}