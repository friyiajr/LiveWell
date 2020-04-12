package com.oneonefivedigitalcreations.easyhrv

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.oneonefivedigitalcreations.easyhrv.database.QuestionnareDatabase
import com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult.QuestionnaireResult
import com.oneonefivedigitalcreations.easyhrv.models.questionnaireresult.QuestionnaireResultDAO
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class QuestionnaireDatabaseTest {

    private lateinit var questionnaireResultDAO: QuestionnaireResultDAO
    private lateinit var db: QuestionnareDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, QuestionnareDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        questionnaireResultDAO = db.questionnaireResultDAO

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
       db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val result = QuestionnaireResult()
        questionnaireResultDAO.insert(result)
        val currentResult = questionnaireResultDAO.getQuestionnaireResultById(0L)
        assertEquals(-1, currentResult?.hrv)
    }
}