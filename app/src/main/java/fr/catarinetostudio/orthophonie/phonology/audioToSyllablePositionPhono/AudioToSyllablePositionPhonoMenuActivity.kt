package fr.insa_cvl.orthophonie.phonology.audioToSyllablePositionPhono

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess
import fr.insa_cvl.orthophonie.phonology.PhonologyMenuActivity
import fr.insa_cvl.orthophonie.phonology.audioToRhymePhono.AudioToRhymeActivity

class AudioToSyllablePositionPhonoMenuActivity : AppCompatActivity() {
    private var adapter_simple : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var phonology_list = databaseAccess.get_MenuAudioToSyllabePosition()


        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,phonology_list)
        var listview = findViewById(R.id.list_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "Position Clicked:"+" "+position, Toast.LENGTH_LONG).show()
            var intent = Intent(this, AudioToSyllablePositionPhonoActivty::class.java)
            intent.putExtra("EXTRA_POSITION",position)
            startActivity(intent)
            finish()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, PhonologyMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
}