package fr.catarinetostudio.orthophonie.articulation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.utils.DatabaseAccess

class DescriptionArtiSerie: AppCompatActivity() {

    private var adapter_simple: ArrayAdapter<String>? = null
    private var lettre_b: String="B"
    private var serie_1: String="1"
    private var index_letter_level: IntArray = intArrayOf(0)
    //join de deux tables recuperer position precedente pour recuperer lettre table lettre
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)
        index_letter_level = intent.getIntArrayExtra("IntArray")
        //Toast.makeText(this, "Position Clicked:"+" "+ index_letter_level, Toast.LENGTH_LONG).show()

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var articulation_level = databaseAccess.getArticulationMot(index_letter_level[1] + 1,index_letter_level[0] + 1)


        adapter_simple = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, articulation_level)
        var listview = findViewById(R.id.list_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, "Position Clicked:"+" "+position, Toast.LENGTH_LONG).show()
            var intent = Intent(this, DescriptionArtiLevel::class.java)
            intent.putExtra("EXTRA_POSITION", position)
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