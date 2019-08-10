package com.example.test

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues


class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_SCORE + " TEXT," + ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)

        // Create tables again
        onCreate(db)
    }

    fun addPlayer(player: PlayerData) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, player.name)
        values.put(COLUMN_USER_SCORE, player.score)


        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }
    fun  updatePlayer(player: PlayerData){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, player.name)
        values.put(COLUMN_USER_SCORE, player.score)
        values.put(COLUM_USER_WINS, player.wins)
        values.put(COLUM_USER_LOSES, player.loses)
        values.put(COLUM_USER_DRAWNS, player.drawns)

        db.update(
            TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(player.name.toString())
        )
    }


    companion object {

        // Database Version
        val DATABASE_VERSION = 1

       // base Name
        val DATABASE_NAME = "UserManager.db"

        //table name
        val TABLE_USER = "user"

       // Table Columns names
        val COLUMN_USER_ID = "user_id"
        val COLUMN_USER_NAME = "user_name"
        val COLUMN_USER_SCORE = "user_score"
        val COLUM_USER_WINS = "user_wins"
        val COLUM_USER_LOSES = "user_loses"
        val COLUM_USER_DRAWNS = "user_drawns"

    }

}