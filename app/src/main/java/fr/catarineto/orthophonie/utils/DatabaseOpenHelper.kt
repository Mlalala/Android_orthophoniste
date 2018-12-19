package fr.catarineto.orthophonie.utils

import android.content.Context

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseOpenHelper(context: Context) : SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "ortho.db"
        private const val DATABASE_VERSION = 1
    }
}