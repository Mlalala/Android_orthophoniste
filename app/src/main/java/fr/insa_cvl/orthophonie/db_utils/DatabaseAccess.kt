package fr.insa_cvl.orthophonie.db_utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DatabaseAccess

private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null

    fun get_AudioToWordPhono(num_serie : Int, num : Int): ArrayList<String>{
        val list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT proposals,answer FROM AudioToWordPhono WHERE No_serie = " + num_serie.toString()+ " AND No = " + num.toString(), null)
        cursor.moveToFirst()

        list.add(cursor.getString(0))
        list.add(cursor.getString(1))

        cursor.close()

        return list
    }

    fun count_AudioToWordPhono(num_serie : Int):Int{
        val cursor = database!!.rawQuery("SELECT COUNT(No)\n" +
                "FROM AudioToWordPhono\n" +
                "WHERE No_serie = " + num_serie.toString(),null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    fun articulation_letter(lettre : String): String{
        val cursor = database!!.rawQuery("SELECT  Lettre From Articulation\n" +
                "WHERE Lettre = " + lettre, null)

        return lettre
    }

    init {
        this.openHelper = DatabaseOpenHelper(context)
    }

    fun get_MemoryPhono(num_serie : Int): ArrayList<String>{
        val cursor = database!!.rawQuery("SELECT elements FROM MemoryPhono WHERE serie = " + num_serie.toString(), null)
        cursor.moveToFirst()
        val list = ArrayList((cursor.getString(0)).split(","))

        cursor.close()

        return list
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