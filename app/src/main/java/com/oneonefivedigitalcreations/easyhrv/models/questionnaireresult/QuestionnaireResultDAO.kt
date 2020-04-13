package com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionnaireResultDAO {
    @Insert
    fun insert(questionnaireResult: QuestionnaireResult)

    @Delete
    fun delete(questionnaireResult: QuestionnaireResult)

    @Update
    fun update(questionnaireResult: QuestionnaireResult)

    @Query("SELECT * FROM questionnaire_results WHERE id = :id")
    fun getQuestionnaireResultById(id: String): QuestionnaireResult?

    @Query("SELECT * FROM questionnaire_results")
    fun getAllQuestionnaireResults(): LiveData<List<QuestionnaireResult>>
}