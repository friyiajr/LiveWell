package com.oneonefivedigitalcreations.easyhrv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult.QuestionnaireResult
import com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult.QuestionnaireResultDAO

@Database(entities = [QuestionnaireResult::class], version = 1, exportSchema = false)
abstract class QuestionnareDatabase : RoomDatabase() {

    abstract val questionnaireResultDAO: QuestionnaireResultDAO

    companion object {
        @Volatile
        private var INSTANCE: QuestionnareDatabase? = null

        fun getInstance(context: Context): QuestionnareDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionnareDatabase::class.java,
                        "questionnaire_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}