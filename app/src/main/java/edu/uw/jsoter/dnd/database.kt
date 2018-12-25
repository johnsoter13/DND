package edu.uw.jsoter.dnd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FeedReaderDbHelper(context: Context, SQL_CREATE_ENTRIES: String, SQL_DELETE_ENTRIES: String) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val sqlCreate = SQL_CREATE_ENTRIES
    private val sqlDelete = SQL_DELETE_ENTRIES

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlCreate)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(sqlDelete)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }
}