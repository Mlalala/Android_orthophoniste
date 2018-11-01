package fr.insa_cvl.orthophonie.phonology

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.insa_cvl.orthophonie.R

class PhonologyMenuActivity : AppCompatActivity() {

    private val menu_title = arrayOf(
            "Du son au mot"
    )

    private val activity_list = arrayOf(
            AudioToWordPhonoMenuActivity::class.java
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}