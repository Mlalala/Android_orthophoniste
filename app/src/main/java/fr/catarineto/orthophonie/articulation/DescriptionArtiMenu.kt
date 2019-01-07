package fr.catarineto.orthophonie.articulation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import fr.catarineto.orthophonie.MainActivity
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess


class DescriptionArtiMenu : AppCompatActivity(){

    private var adapter_simple : ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var articulation_lettre = databaseAccess.getArticulationLetter()


        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,articulation_lettre)
        var listview = findViewById<ListView>(R.id.list_menu)
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { _, _, position, _ ->
            var intent = Intent(this, DescriptionArtiActivity::class.java)
            intent.putExtra("EXTRA_POSITION",position)
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