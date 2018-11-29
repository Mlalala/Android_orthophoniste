package fr.insa_cvl.orthophonie.phonology

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.insa_cvl.orthophonie.*
import fr.insa_cvl.orthophonie.phonology.audioToWordPhono.AudioToWordPhonoMenuActivity
import fr.insa_cvl.orthophonie.phonology.memoryPhono.MemoryPhonoMenuActivity
import fr.insa_cvl.orthophonie.phonology.pictureToPhonemePhono.PictureToPhonemePhonoMenuActivity
import fr.insa_cvl.orthophonie.phonology.audioToRhymePhono.AudioToRhymeMenuActivity
import fr.insa_cvl.orthophonie.phonology.audioToSyllablePositionPhono.AudioToSyllablePositionPhonoMenuActivity

class PhonologyMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        val menu_title = arrayOf(
                getString(R.string.title_AudioToWordPhono),
                getString(R.string.title_MemoryPhono),
                getString(R.string.title_PictureToPhonemePhono),
                getString(R.string.title_AudioToRhyme),
                getString(R.string.title_AudioToSyllablePositionPhono)
        )

        val menu_des = arrayOf(
                getString(R.string.des_AudioToWordPhono),
                getString(R.string.des_MemoryPhono),
                getString(R.string.des_PictureToPhonemePhono),
                getString(R.string.des_AudioToRhyme),
                getString(R.string.des_AudioToSyllablePositionPhono)
        )

        val activity_list = arrayOf(
                AudioToWordPhonoMenuActivity::class.java,
                MemoryPhonoMenuActivity::class.java,
                PictureToPhonemePhonoMenuActivity::class.java,
                AudioToRhymeMenuActivity::class.java,
                AudioToSyllablePositionPhonoMenuActivity::class.java
        )

        val menu_picture = arrayOf(
                "phono1",
                "sample",
                "sample",
                "sample",
                "sample"
        )

        val menuList = ArrayList<ModuleMenuItem>()

        for (i in 0..menu_picture.lastIndex) {
            menuList.add(ModuleMenuItem(menu_title[i], menu_des[i], menu_picture[i],activity_list[i]))
        }

        val menuAdapter = ModuleMenuListAdapter(this, R.layout.module_menu_list_layout, menuList)

        var list = findViewById(R.id.list_menu) as ListView

        list.adapter = menuAdapter


        list.onItemClickListener = AdapterView.OnItemClickListener{ adapter, v, position, id ->
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