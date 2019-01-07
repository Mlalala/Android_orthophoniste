package fr.catarineto.orthophonie.phonology.audioToRhymePhono

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.catarineto.orthophonie.MenuExercicesActivity
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess

class AudioToRhymeMenuActivity: AppCompatActivity() {
    private var adapter_simple : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var phonology_list = databaseAccess.getMenuAudioToRhyme()


        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,phonology_list)
        var listview = findViewById(R.id.list_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { _, _, position, _ ->
            var intent = Intent(this, AudioToRhymeActivity::class.java)
            intent.putExtra("EXTRA_POSITION",position)
            startActivity(intent)
            finish()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MenuExercicesActivity::class.java)
            intent.putExtra("EXTRA_POSITION",1)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
}