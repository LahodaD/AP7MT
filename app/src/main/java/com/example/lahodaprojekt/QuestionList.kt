package com.example.lahodaprojekt

class QuestionList(
    val category: String,
    val id: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val question: String,
    val tags: List<String>,
    val type: String,
    val difficulty: String,
    val regions: List<String>,
    var isNiche: Boolean
) {

}