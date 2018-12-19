package fr.catarinetostudio.orthophonie.phonology

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.ListView
import fr.catarinetostudio.orthophonie.utils.Ads
import fr.catarinetostudio.orthophonie.MainActivity
import fr.catarinetostudio.orthophonie.utils.ModuleMenuItem
import fr.catarinetostudio.orthophonie.utils.ModuleMenuListAdapter
import fr.catarinetostudio.orthophonie.*
import fr.catarinetostudio.orthophonie.phonology.audioToWordPhono.AudioToWordPhonoMenuActivity
import fr.catarinetostudio.orthophonie.phonology.memoryPhono.MemoryPhonoMenuActivity
import fr.catarinetostudio.orthophonie.phonology.pictureToPhonemePhono.PictureToPhonemePhonoMenuActivity
import fr.catarinetostudio.orthophonie.phonology.audioToRhymePhono.AudioToRhymeMenuActivity
import fr.catarinetostudio.orthophonie.phonology.audioToSyllablePositionPhono.AudioToSyllablePositionPhonoMenuActivity

class PhonologyMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        val menuTitle = arrayOf(
                getString(R.string.title_PictureToPhonemePhono),
                getString(R.string.title_AudioToWordPhono),
                getString(R.string.title_MemoryPhono),
                getString(R.string.title_AudioToRhyme),
                getString(R.string.title_AudioToSyllablePositionPhono)
        )

        val menuDes = arrayOf(
                getString(R.string.des_PictureToPhonemePhono),
                getString(R.string.des_AudioToWordPhono),
                getString(R.string.des_MemoryPhono),
                getString(R.string.des_AudioToRhyme),
                getString(R.string.des_AudioToSyllablePositionPhono)
        )

        val activityList = arrayOf(
                PictureToPhonemePhonoMenuActivity::class.java,
                AudioToWordPhonoMenuActivity::class.java,
                MemoryPhonoMenuActivity::class.java,
                AudioToRhymeMenuActivity::class.java,//AudioToRhymeMenuActivity::class.java,
                AudioToSyllablePositionPhonoMenuActivity::class.java//AudioToSyllablePositionPhonoMenuActivity::class.java
        )

        val menuPicture = arrayOf(
                "PictureToPhonemePhono",
                "AudioToWordPhono",
                "MemoryPhono",
                "AudioToRhyme",
                "AudioToSyllablePosition"
        )

        val menuAudio = arrayOf(
                "helptest",
                "helptest",
                "helptest",
                "helptest",
                "helptest"
        )

        val menuAcTitle = arrayOf(
                getString(R.string.title_PictureToPhonemePhono),
                getString(R.string.title_AudioToWordPhono),
                getString(R.string.title_MemoryPhono),
                getString(R.string.title_AudioToRhyme),
                getString(R.string.title_AudioToSyllablePositionPhono)
        )



        val menuList = ArrayList<ModuleMenuItem>()

        for (i in 0..menuPicture.lastIndex) {
            menuList.add(ModuleMenuItem(menuTitle[i], menuDes[i], menuPicture[i], menuAudio[i] ,activityList[i],menuAcTitle[i]))
        }

        val menuAdapter = ModuleMenuListAdapter(this@PhonologyMenuActivity, R.layout.module_menu_list_layout, menuList)

        val list = findViewById<ListView>(R.id.list_menu)

        list.adapter = menuAdapter


        list.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            val intent = Intent(this, activityList[position])
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