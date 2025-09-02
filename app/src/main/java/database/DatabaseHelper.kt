package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME: String = "words.db"
        private const val DB_VERSION: Int = 1
        private const val TABLE_NAME: String = "en_words"
        private const val COL_ID: String = "id"
        private const val COL_WORD: String = "word"
        private const val COL_TRANSCRIPT: String = "transcription"
        private const val COL_TRANSLATE: String = "translation"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateTable: String = ("create table $TABLE_NAME ($COL_ID integer private key unique, $COL_WORD text, $COL_TRANSCRIPT text, $COL_TRANSLATE)")

        db!!.execSQL(queryCreateTable)
    }

    fun onDrop(db: SQLiteDatabase?) {
        val queryDropTable: String = ("drop table if exist $TABLE_NAME")
        db!!.execSQL(queryDropTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onDrop(db)
        onCreate(db)
    }
}