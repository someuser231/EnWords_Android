package database

import android.content.ContentValues
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
        private const val COL_TC_US: String = "transcription_us"
        private const val COL_TC_UK: String = "transcription_uk"
        private const val COL_WORD_FORM: String = "word_form"
        private const val COL_TL: String = "translation"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateTable: String = ("create table $TABLE_NAME ($COL_ID integer primary key unique, $COL_WORD text, $COL_TC_US text, $COL_TC_UK text, $COL_WORD_FORM text, $COL_TL text)")
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

    fun AddData(word: String, tc_us: String, tc_uk: String, wordForm: Array<String>, tl: Array<String>) {
        val values = ContentValues().apply {
            put(COL_WORD, word)
            put(COL_TC_US, tc_us)
            put(COL_TC_UK, tc_uk)
            var strWordForm = "-"
            for (i in wordForm) {
                strWordForm += "$i | "
            }
            put(COL_WORD_FORM, strWordForm)
            var strTl = ""
            for (i in tl) {
                strTl += "$i | "
            }
            put(COL_TL, strTl)
        }
        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun ReadData(): MutableList<WordStructure> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COL_ID, COL_WORD, COL_TC_US, COL_TC_UK, COL_WORD_FORM, COL_TL), null, null, null, null, COL_ID)
        val values = mutableListOf<WordStructure>()

        if (cursor.moveToFirst()) {
            do {
                val word = WordStructure().apply {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
                    word = cursor.getString(cursor.getColumnIndexOrThrow(COL_WORD))
                    tcUs = cursor.getString(cursor.getColumnIndexOrThrow(COL_TC_US))
                    tcUk = cursor.getString(cursor.getColumnIndexOrThrow(COL_TC_UK))
                    wordForm = cursor.getString(cursor.getColumnIndexOrThrow(COL_WORD_FORM))
                    tl = cursor.getString(cursor.getColumnIndexOrThrow(COL_TL))
                }
                values.add(word)
            }
            while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return values
    }

    fun GetElement(id: Int): WordStructure {
        val db = readableDatabase
        val cursor = db.rawQuery("select * from $TABLE_NAME where $COL_ID = $id", null)
        val word = WordStructure()
        if (cursor.moveToFirst()) {
            word.id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            word.word = cursor.getString(cursor.getColumnIndexOrThrow(COL_WORD))
            word.tcUs = cursor.getString(cursor.getColumnIndexOrThrow(COL_TC_US))
            word.tcUk = cursor.getString(cursor.getColumnIndexOrThrow(COL_TC_UK))
            word.wordForm = cursor.getString(cursor.getColumnIndexOrThrow(COL_WORD_FORM))
            word.tl = cursor.getString(cursor.getColumnIndexOrThrow(COL_TL))
        }
        cursor.close()
        db.close()
        return word
    }

    fun DeleteElement(id: Int) {
        val db = writableDatabase
        val queryDelData = ("delete from $TABLE_NAME where $COL_ID = $id")
        db.execSQL(queryDelData)
        db.close()
    }

    fun ModifyElement(word: WordStructure) {
        val db = writableDatabase
        val query = ("update $TABLE_NAME set $COL_WORD = '${word.word}'," +
                "$COL_TC_US = '${word.tcUs}', $COL_TC_UK = '${word.tcUk}'," +
                "$COL_WORD_FORM = '${word.wordForm}', $COL_TL = '${word.tl}' where $COL_ID = ${word.id}")
        db.execSQL(query)
        db.close()
    }
}