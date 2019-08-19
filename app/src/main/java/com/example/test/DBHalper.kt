package com.example.test

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import java.lang.Exception

// Todo add gamecount and id, wins, drawns, loses
class DBHalper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_SCORE + " INTEGER,"
            + COLUMN_USER_GAME_COUNT+ " INTEGER,"
            + COLUM_USER_WINS + " INTEGER,"
            + COLUM_USER_LOSES + " INTEGER,"
            + COLUM_USER_DRAWNS + " INTEGER"+ ")")

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

    fun checkExistingPlayer(nameOfUser: String): Boolean {
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // sorting orders
        val selection = "$COLUMN_USER_NAME =?"
        val selectionArgs = arrayOf(nameOfUser)




        // query the user table
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null
        )  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    fun getPlayer(nameOfUser: String):PlayerData{
        val columns = arrayOf(COLUMN_USER_ID, COLUM_USER_WINS, COLUMN_USER_NAME, COLUM_USER_LOSES, COLUM_USER_DRAWNS)
        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"

        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder
        )
        //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = PlayerData(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    GameCount = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_GAME_COUNT)),
                    score = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_SCORE)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    wins = cursor.getInt(cursor.getColumnIndex(COLUM_USER_WINS)),
                    loses = cursor.getInt(cursor.getColumnIndex(COLUM_USER_LOSES)),
                    drawns = cursor.getInt(cursor.getColumnIndex(COLUM_USER_DRAWNS))
                )
                if (user.name == nameOfUser) {
                    cursor.close()
                    db.close()
                    return user

                }
            } while (cursor.moveToNext())
        }
//        ToDo: make logic for returning non-null id
        val user = PlayerData(
            id = null,
            GameCount = 0,
            score = 0,
            name = nameOfUser,
            wins = 0,
            loses = 0,
            drawns = 0
        )
        cursor.close()
        db.close()
        return user
    }

    fun getUserID(nameOfUser: String):Int? {
        val columns = arrayOf(COLUMN_USER_ID, COLUM_USER_WINS, COLUMN_USER_NAME, COLUM_USER_LOSES, COLUM_USER_DRAWNS)
        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"

        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder
        )
        //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = PlayerData(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    GameCount = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_GAME_COUNT)),
                    score = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_SCORE)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    wins = cursor.getInt(cursor.getColumnIndex(COLUM_USER_WINS)),
                    loses = cursor.getInt(cursor.getColumnIndex(COLUM_USER_LOSES)),
                    drawns = cursor.getInt(cursor.getColumnIndex(COLUM_USER_DRAWNS))
                )
                if (user.name == nameOfUser) {
                    cursor.close()
                    db.close()
                    return user.id

                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return null
    }

    fun getLastID():Int {
        val columns = arrayOf(COLUMN_USER_ID, COLUM_USER_WINS, COLUMN_USER_NAME, COLUM_USER_LOSES, COLUM_USER_DRAWNS)
        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"

        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder
        )
        cursor.moveToLast()
        val lastId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt()
        print(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt())
        if (cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt() == null) {
            val lastId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt()
            db.close()
            return lastId
        } else
            return 0
    }

    fun  updatePlayer(player: PlayerData){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_ID, player.id)
        values.put(COLUMN_USER_NAME, player.name)
        values.put(COLUMN_USER_GAME_COUNT,player.GameCount)
        values.put(COLUMN_USER_SCORE, player.score)
        values.put(COLUM_USER_WINS, player.wins)
        values.put(COLUM_USER_LOSES, player.loses)
        values.put(COLUM_USER_DRAWNS, player.drawns)

        db.update(
            TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(player.name.toString())
        )
    }



    fun getAllrows(): ArrayList<PlayerData>{
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUM_USER_WINS, COLUMN_USER_NAME, COLUM_USER_LOSES, COLUM_USER_DRAWNS)

        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<PlayerData>()

        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(
            TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder
        )         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = PlayerData(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    GameCount = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_GAME_COUNT)),
                    score = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_SCORE)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    wins = cursor.getInt(cursor.getColumnIndex(COLUM_USER_WINS)),
                    loses = cursor.getInt(cursor.getColumnIndex(COLUM_USER_LOSES)),
                    drawns = cursor.getInt(cursor.getColumnIndex(COLUM_USER_DRAWNS))
                )

                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()


        return userList


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
        val COLUMN_USER_GAME_COUNT ="user_game_count"
        val COLUMN_USER_NAME = "user_name"
        val COLUMN_USER_SCORE = "user_score"
        val COLUM_USER_WINS = "user_wins"
        val COLUM_USER_LOSES = "user_loses"
        val COLUM_USER_DRAWNS = "user_drawns"

    }

}