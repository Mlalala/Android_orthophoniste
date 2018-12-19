package fr.catarineto.orthophonie.utils

import android.support.v7.app.AppCompatActivity

data class ModuleMenuItem(val titre : String, val des : String, val pic : String, val audio : String, val act : Class<out AppCompatActivity>,val titleAc : String) {
}