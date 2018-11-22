package fr.insa_cvl.orthophonie.db_utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DatabaseAccess

private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null

    init {
        this.openHelper = DatabaseOpenHelper(context)
    }


    //// PHONOLOGIE ////

        // PictureToPhonemePhono

    fun get_Menu_PictureToPhonemePhono(): ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT serie,name FROM PictureToPhonemeMenuPhono",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun get_proposals_PictureToPhonemePhono(num_serie : Int): List<String>{
        val cursor = database!!.rawQuery("SELECT proposals FROM PictureToPhonemeMenuPhono WHERE serie = " + num_serie.toString(),null)
        cursor.moveToFirst()
        var list = cursor.getString(0).split(",")
        cursor.close()
        return list
    }

    fun get_answer_PictureToPhonemePhono(num_serie : Int, No : Int): Int{
        val cursor = database!!.rawQuery("SELECT answer FROM PictureToPhonemePhono WHERE serie = " + num_serie.toString() + " AND No = " + No.toString() ,null)
        cursor.moveToFirst()
        var answer = cursor.getInt(0)
        cursor.close()
        return answer
    }

    fun count_PictureToPhonemePhono(num_serie : Int):Int{
        val cursor = database!!.rawQuery("SELECT COUNT(No)\n" +
                "FROM PictureToPhonemePhono\n" +
                "WHERE serie = " + num_serie.toString(),null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

        // AudioToWordPhono

    fun get_Menu_AudioToWordPhono(): ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT serie,name FROM AudioToWordMenuPhono",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

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

        //MemoryPhono

    fun get_Menu_MemoryPhono(): ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT serie,name FROM MemoryPhono",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun get_MemoryPhono(num_serie : Int): ArrayList<String>{
        val cursor = database!!.rawQuery("SELECT elements FROM MemoryPhono WHERE serie = " + num_serie.toString(), null)
        cursor.moveToFirst()
        val list = ArrayList((cursor.getString(0)).split(","))

        cursor.close()

        return list
    }

        //audio to rhyme
    fun get_menu_AudioToRhyme():ArrayList<String>{
            var list = ArrayList<String>()
            val cursor = database!!.rawQuery("SELECT serie,name FROM AudioToRhymeMenu",null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }

    fun count_AudioToRhyme(num_serie : Int):Int{
        val cursor = database!!.rawQuery("SELECT num FROM AudioToRhyme WHERE serie = " + num_serie.toString(),null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    fun get_AudioToRhyme(num_serie : Int, num : Int): ArrayList<String>{
        val list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT proposals,answer FROM AudioToRhyme WHERE serie = " + num_serie.toString()+ " AND num = " + num.toString(), null)
        cursor.moveToFirst()

        list.add(cursor.getString(0))
        list.add(cursor.getString(1))

        cursor.close()

        return list
    }

    // syllabe position

    fun get_MenuAudioToSyllabePosition():ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT serie,name FROM SyllabePositionMenu",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    //// VISUAL ////

        // MemorySyllabesVisu

    fun get_Menu_MemorySyllabesVisu(): ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT serie,name FROM MemorySyllabesVisu",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun get_MemorySyllabesVisu(num_serie : Int): ArrayList<String>{
        val cursor = database!!.rawQuery("SELECT elements FROM MemorySyllabesVisu WHERE serie = " + num_serie.toString(), null)
        cursor.moveToFirst()
        val list = ArrayList((cursor.getString(0)).split(","))

        cursor.close()

        return list
    }

        // SearchSyllableVisu

    fun get_Menu_SearchSyllableVisu(): ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT serie,name FROM SearchSyllableVisu",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add("Série " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun get_SearchSyllableVisu(num_serie : Int): ArrayList<String>{
        val cursor = database!!.rawQuery("SELECT syllables FROM SearchSyllableVisu WHERE serie = " + num_serie.toString(), null)
        cursor.moveToFirst()
        val list = ArrayList((cursor.getString(0)).split(","))

        cursor.close()

        return list
    }

    fun get_Answers_SearchSyllableVisu(num_serie : Int): ArrayList<String>{
        val cursor = database!!.rawQuery("SELECT answers FROM SearchSyllableVisu WHERE serie = " + num_serie.toString(), null)
        cursor.moveToFirst()
        val list = ArrayList((cursor.getString(0)).split(","))

        cursor.close()

        return list
    }

    //// ARTICULATION ////

    fun get_Articulation_level(index_lettre: Int):ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT lettre,name FROM DescriptionArtiSeries WHERE num = "  +  index_lettre.toString(),null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add(" " + cursor.getString(0) + " - " + cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun get_Articulation_letter():ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT letter FROM DescriptionArtiLettre",null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add(" " + cursor.getString(0))
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun get_Articulation_mot(num : Int, serie : Int):ArrayList<String>{
        var list = ArrayList<String>()
        val cursor = database!!.rawQuery("SELECT name FROM DescriptionArtiObjet WHERE num = " + "'" + num.toString() + "'" + "AND serie= "+ serie.toString(), null)
        //val cursor = database!!.rawQuery("SELECT name FROM DescriptionArtiObjet LEFT JOIN DescriptionArtiSeries ON WHERE lettre = " + "'" + lettre + "'" + "AND serie= "+ serie, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
            list.add(" " + cursor.getString(0))
            cursor.moveToNext()
        }

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