package fr.insa_cvl.orthophonie.phonology

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.insa_cvl.orthophonie.Ads
import fr.insa_cvl.orthophonie.MainActivity
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.phonology.audioToWordPhono.AudioToWordPhonoMenuActivity
import fr.insa_cvl.orthophonie.phonology.memoryPhono.MemoryPhonoMenuActivity
import fr.insa_cvl.orthophonie.phonology.pictureToPhonemePhono.PictureToPhonemePhonoMenuActivity
import fr.insa_cvl.orthophonie.phonology.audioToRhymePhono.AudioToRhymeMenuActivity

class PhonologyMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        val menu_title = arrayOf(
                getString(R.string.title_AudioToWordPhono),
                getString(R.string.title_MemoryPhono),
                getString(R.string.title_PictureToPhonemePhono),
                getString(R.string.title_AudioToRhyme)
        )

        val activity_list = arrayOf(
                AudioToWordPhonoMenuActivity::class.java,
                MemoryPhonoMenuActivity::class.java,
                PictureToPhonemePhonoMenuActivity::class.java,
                AudioToRhymeMenuActivity::class.java
        )

        var adapter_simple : ArrayAdapter<String>? = null

        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,menu_title)
        var listview = findViewById(R.id.list_menu) as ListView
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