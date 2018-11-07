package fr.insa_cvl.orthophonie.articulation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.insa_cvl.orthophonie.MainActivity
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.phonology.audioToWordPhono.AudioToWordPhonoMenuActivity


class ArticulationActivity : AppCompatActivity(){

    private val articulation_list = arrayListOf<String>(
            "LE B",
            "LE CH",
            "LE D",
            "LE F",
            "LE G",
            "Le J",
            "LE K",
            "LE L",
            "LES BL FL GL KL PL",
            "LE M"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)


        val activity_list = arrayOf(
                AudioToWordPhonoMenuActivity::class.java
        )

        val menu_title = arrayOf(
                getString(R.string.title_arti)
        )

        var adapter_simple : ArrayAdapter<String>? = null

        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,articulation_list)
        var listview = findViewById(R.id.articulation_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, activity_list[position])
            startActivity(intent)
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)

    }
}