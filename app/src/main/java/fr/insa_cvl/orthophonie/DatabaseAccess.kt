package fr.insa_cvl.orthophonie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DatabaseAccess

private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null

    fun get_serie(num_serie : Int, num : Int): ArrayList<String>{
        val list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT proposals,answer FROM series WHERE No_serie = " + num_serie.toString()+ " AND No = " + num.toString(), null)
        cursor.moveToFirst()

        list.add(cursor.getString(0))
        list.add(cursor.getString(1))

        cursor.close()

        return list
    }

    fun count(num_serie : Int):Int{
        val cursor = database!!.rawQuery("SELECT COUNT(No)\n" +
                "FROM series\n" +
                "WHERE No_serie = " + num_serie.toString(),null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    init {
        this.openHelper = DatabaseOpenHelper(context)
    }


    fun open() {
        this.database = openHelper.writableDatabase
    }

    fun close() {
        if (database != null) {
            this.database!!.close()
        }
    }

    companion object {
        private var instance: DatabaseAccess? = null

        fun getInstance(context: Context): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance!!
        }
    }
}