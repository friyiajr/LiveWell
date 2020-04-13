package com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * A class representing a result from the wellness questionnaire
 */
@Entity(tableName = "questionnaire_results")
data class QuestionnaireResult(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var userId: String = "",
    var heartRate: Int = -1,
    var hrv: Int = -1,
    var sleepRating: Int = -1,
    var stress: Int = -1,
    var water: Int = -1,
    var supplementation: Boolean = false,
    val completedAt: Long = System.currentTimeMillis()
)