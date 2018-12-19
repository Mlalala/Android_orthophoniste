package fr.catarineto.orthophonie.articulation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess

class DescriptionArtiLevel: AppCompatActivity() {

    private var adapter_simple : ArrayAdapter<String>? = null
    private var lettre_b: String="B"
    private var index_letter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        index_letter = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var articulation_level = databaseAccess.getArticulationLevel(index_letter +1)


        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,articulation_level)
        var listview = findViewById(R.id.list_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, "Position Clicked:"+" "+position, Toast.LENGTH_LONG).show()
            var lettre_level: IntArray= intArrayOf(position,index_letter)
            var intent = Intent(this, DescriptionArtiSerie::class.java)
            intent.putExtra("IntArray",lettre_level)
            //Toast.makeText(this, "test"+lettre_level, Toast.LENGTH_LONG).show()
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