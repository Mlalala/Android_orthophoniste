package fr.catarineto.orthophonie.phonology.audioToWordPhono

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.catarineto.orthophonie.utils.Ads
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess
import fr.catarineto.orthophonie.phonology.PhonologyMenuActivity

class AudioToWordPhonoMenuActivity : AppCompatActivity(){

     private var adapterSimple : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val phonologyList = databaseAccess.getMenuAudioToWordPhono()


        adapterSimple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,phonologyList)
        val listview = findViewById<ListView>(R.id.list_menu)
        listview.adapter = adapterSimple


        listview.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, AudioToWordPhonoActivity::class.java)
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