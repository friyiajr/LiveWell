package com.oneonefivedigitalcreations.easyhrv.survey.questionnaire

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionnaireViewModel : ViewModel() {

    private val questions: Map<String, QuestionType> = mapOf(
        "How would you rate the quality of your sleep last night?" to QuestionType.SCALE,
        "How would you rate your stress level yesterday?" to QuestionType.SCALE,
        "How much water did you drink yesterday" to QuestionType.QUANTITY,
        "Did you take all required vitamins today?" to QuestionType.BOOLEAN
    )

    private var responses: MutableList<Any> = mutableListOf()

    val currentQuestionString: MutableLiveData<String> =
        MutableLiveData(questions.keys.elementAt(0))

    val currentQuestionType: MutableLiveData<QuestionType> =
        MutableLiveData(questions.values.elementAt(0))

    private var currentQuestionIndex: Int = 0

    init {
        Log.i("Questionnaire View Model", "Created!")
    }

    fun startNextQuestion(result: Any) {
        responses.add(result)
        currentQuestionIndex += 1
        currentQuestionString.value = questions.keys.elementAt(currentQuestionIndex)
        currentQuestionType.value = questions.values.elementAt(currentQuestionIndex)
    }
}

enum class QuestionType {
    SCALE,
    BOOLEAN,
    QUANTITY,
}