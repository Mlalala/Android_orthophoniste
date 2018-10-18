package fr.insa_cvl.orthophonie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DatabaseAccess
/**
 * Private constructor to aboid object creation from outside classes.
 *
 * @param context
 */
private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    fun get_serie(num_serie : Int, num : Int): ArrayList<String>{
        val list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT proposals,answer FROM series WHERE No_serie = " + num_serie.toString()+ " AND No = " + num.toString(), null)
        cursor.moveToFirst()

        list.add(cursor.getString(0))
        list.add(cursor.getString(1))

        cursor.close()

        return list
    }


    init {
        this.openHelper = DatabaseOpenHelper(context)
    }

    /**
     * Open the database connection.
     */
    fun open() {
        this.database = openHelper.writableDatabase
    }

    /**
     * Close the database connection.
     */
    fun close() {
        if (database != null) {
            this.database!!.close()
        }
    }

    companion object {
        private var instance: DatabaseAccess? = null

        /**
         * Return a singleton instance of DatabaseAccess.
         *
         * @param context the Context
         * @return the instance of DabaseAccess
         */
        fun getInstance(context: Context): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance!!
        }
    }
}