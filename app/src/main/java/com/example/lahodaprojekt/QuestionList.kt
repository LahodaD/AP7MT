package com.example.lahodaprojekt

import android.os.Parcel
import android.os.Parcelable

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(id)
        parcel.writeString(correctAnswer)
        parcel.writeStringList(incorrectAnswers)
        parcel.writeString(question)
        parcel.writeStringList(tags)
        parcel.writeString(type)
        parcel.writeString(difficulty)
        parcel.writeStringList(regions)
        parcel.writeByte(if (isNiche) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionList> {
        override fun createFromParcel(parcel: Parcel): QuestionList {
            return QuestionList(parcel)
        }

        override fun newArray(size: Int): Array<QuestionList?> {
            return arrayOfNulls(size)
        }
    }
}