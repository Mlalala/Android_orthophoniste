package fr.insa_cvl.orthophonie.articulation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.insa_cvl.orthophonie.R


class DescriptionArtiLevel: AppCompatActivity() {


    private var lettre : Int = 0

    private val level_list = arrayListOf<String>(
            "Initiale",
            "Médiane",
            "Final"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)
        //lettre = intent.getIntExtra("EXTRA_POSITION",0)

        val choice_list = arrayOf(
                DescriptionArtiInitiallevel::class.java,
                DescriptionArtiMedianelevel::class.java,
                DescriptionArtiFinallevel::class.java
        )

        var adapter_simple : ArrayAdapter<String>? = null

        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,level_list)
        var listview = findViewById(R.id.articulation_menu) as ListView
        listview.adapter = adapter_simple



        listview.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, choice_list[position])
            intent.putExtra("EXTRA_POSITION",position)
            startActivity(intent)
            finish()
            }



    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, DescriptionArtiActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)






    }






}