package fr.insa_cvl.orthophonie

import android.app.Activity
import android.support.v7.app.AppCompatActivity

data class ModuleMenuItem(val titre : String, val des : String, val pic : String, val act : Class<out AppCompatActivity>) {
}