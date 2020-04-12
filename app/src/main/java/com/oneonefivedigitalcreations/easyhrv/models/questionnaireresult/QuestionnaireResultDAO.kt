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

    @Query("SELECT * FROM questionnaire_result WHERE id = :id LIMIT 1")
    fun getQuestionnaireResultById(id: Long): QuestionnaireResult?

    @Query("SELECT * FROM questionnaire_result")
    fun getAllQuestionnaireResults(): LiveData<List<QuestionnaireResult>>
}