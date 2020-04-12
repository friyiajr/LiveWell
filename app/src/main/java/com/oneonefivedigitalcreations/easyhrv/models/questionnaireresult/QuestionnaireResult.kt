package com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A class representing a result from the wellness questionnaire
 */
@Entity(tableName = "questionnaire_result")
data class QuestionnaireResult(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "user_id")
    var userId: String = "",
    @ColumnInfo(name = "heart_rate")
    var heartRate: Int = -1,
    @ColumnInfo(name = "heart_rate_variability")
    var hrv: Int = -1,
    @ColumnInfo(name = "sleep_rating")
    var sleepRating: Int = -1,
    var stress: Int = -1,
    var water: Int = -1,
    var supplementation: Boolean = false,
    @ColumnInfo(name = "completed_at")
    val completedAt: Long = System.currentTimeMillis()
)