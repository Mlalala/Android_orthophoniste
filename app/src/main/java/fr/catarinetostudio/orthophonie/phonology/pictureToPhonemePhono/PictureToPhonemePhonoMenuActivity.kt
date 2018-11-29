package fr.catarinetostudio.orthophonie.phonology.pictureToPhonemePhono

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.catarinetostudio.orthophonie.Ads
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.db_utils.DatabaseAccess
import fr.catarinetostudio.orthophonie.phonology.PhonologyMenuActivity

class PictureToPhonemePhonoMenuActivity : AppCompatActivity(){

    private var adapter_simple : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var list = databaseAccess.get_Menu_PictureToPhonemePhono()
        databaseAccess.close()

        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list)
        var listview = findViewById(R.id.list_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            var intent = Intent(this, PictureToPhonemePhonoActivity::class.java)
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