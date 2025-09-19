package database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

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
        val queryCreateTable: String = ("create table $TABLE_NAME ($COL_ID integer primary key unique, $COL_WORD text, $COL_TRANSCRIPT text, $COL_TRANSLATE)")
        db!!.execSQL(queryCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onDrop(db)
        onCreate(db)
    }

    fun onDrop(db: SQLiteDatabase?) {
        val queryDropTable: String = ("drop table if exists $TABLE_NAME")
        db!!.execSQL(queryDropTable)
    }

    fun AddData(word: String, tc: String, tl: String) {
        val values = ContentValues().apply {
            put(COL_WORD, word)
            put(COL_TRANSCRIPT, tc)
            put(COL_TRANSLATE, tl)
        }
        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun ReadData(): MutableList<WordStructure> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COL_ID, COL_WORD, COL_TRANSCRIPT, COL_TRANSLATE), null, null, null, null, COL_ID)
        val values = mutableListOf<WordStructure>()

        if (cursor.moveToFirst()) {
            do {
                val word = WordStructure().apply {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                    word = cursor.getString(cursor.getColumnIndexOrThrow(COL_WORD))
                    tc = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSCRIPT))
                    tl = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSLATE))
                }
                values.add(word)
            }
            while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return values
    }

    fun DeleteData(id: Int) {
        val db = writableDatabase
        val queryDelData = ("delete from $TABLE_NAME where $COL_ID = $id")
        db.execSQL(queryDelData)
        db.close()
    }
}