package fr.insa_cvl.orthophonie.articulation

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess


class Articulationlevel: AppCompatActivity() {


    private var lettre : Int = 0

    private val level_list = arrayListOf<String>(
            "Initiale",
            "Médiane",
            "Final"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)
        lettre = intent.getIntExtra("EXTRA_POSITION",0)

        var adapter_simple : ArrayAdapter<String>? = null

        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,level_list)
        var listview = findViewById(R.id.articulation_menu) as ListView
        listview.adapter = adapter_simple






    }






}