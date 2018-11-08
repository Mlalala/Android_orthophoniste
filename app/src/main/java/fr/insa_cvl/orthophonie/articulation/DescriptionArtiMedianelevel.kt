package fr.insa_cvl.orthophonie.articulation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import fr.insa_cvl.orthophonie.MainActivity
import fr.insa_cvl.orthophonie.R

class DescriptionArtiMedianelevel: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, DescriptionArtiLevel::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)


    }
}