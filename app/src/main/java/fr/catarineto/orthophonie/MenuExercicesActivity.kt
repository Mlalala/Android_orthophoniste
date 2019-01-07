package fr.catarineto.orthophonie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.ListView
import fr.catarineto.orthophonie.articulation.DescriptionArtiMenu
import fr.catarineto.orthophonie.memory.AudioToOrderMemo.AudioToOrderMemoMenuActivity
import fr.catarineto.orthophonie.memory.SymbolMemo.SymbolMemoActivity
import fr.catarineto.orthophonie.phonology.audioToRhymePhono.AudioToRhymeMenuActivity
import fr.catarineto.orthophonie.phonology.audioToSyllablePositionPhono.AudioToSyllablePositionPhonoMenuActivity
import fr.catarineto.orthophonie.phonology.audioToWordPhono.AudioToWordPhonoMenuActivity
import fr.catarineto.orthophonie.phonology.memoryPhono.MemoryPhonoMenuActivity
import fr.catarineto.orthophonie.phonology.pictureToPhonemePhono.PictureToPhonemePhonoMenuActivity
import fr.catarineto.orthophonie.utils.Ads
import fr.catarineto.orthophonie.utils.ModuleMenuItem
import fr.catarineto.orthophonie.utils.ModuleMenuListAdapter
import fr.catarineto.orthophonie.visual.memorySyllabesVisu.MemorySyllabesVisuMenuActivity
import fr.catarineto.orthophonie.visual.searchSyllableVisu.SearchSyllableVisuMenuActivity

class MenuExercicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        val extra = intent.getIntExtra("EXTRA_POSITION",0)

        when(extra){
            0 -> {

                //////////////////
                // ARTICULATION //
                //////////////////
                val intent = Intent(this, DescriptionArtiMenu::class.java)
                startActivity(intent)
                finish()
            }

            1 -> {

                ///////////////
                // PHONOLOGY //
                ///////////////

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

                process(menuTitle, menuDes, menuPicture, menuAudio,activityList,menuAcTitle)
            }

            2 -> {

                ////////////
                // VISUAL //
                ////////////

                val menuTitle = arrayOf(
                        getString(R.string.title_MemorySyllablesVisu),
                        getString(R.string.title_SearchSyllableVisu)
                )

                val menuDes = arrayOf(
                        getString(R.string.des_MemorySyllablesVisu),
                        getString(R.string.des_SearchSyllableVisu)
                )

                val menuPicture = arrayOf(
                        "MemorySyllablesVisu",
                        "SearchSyllableVisu"
                )

                val menuAudio = arrayOf(
                        "helptest",
                        "helptest"
                )

                val activityList = arrayOf(
                        MemorySyllabesVisuMenuActivity::class.java,
                        SearchSyllableVisuMenuActivity::class.java
                )

                val menuAcTitle = arrayOf(
                        getString(R.string.title_MemorySyllablesVisu),
                        getString(R.string.title_SearchSyllableVisu)
                )

                process(menuTitle, menuDes, menuPicture, menuAudio,activityList,menuAcTitle)
            }

            else -> {

                ////////////
                // MEMORY //
                ////////////

                val menuTitle = arrayOf<String>(
                        getString(R.string.title_AudioToOrderMemo),
                        getString(R.string.title_SymbolsMemo)
                )

                val menuDes = arrayOf<String>(
                        getString(R.string.des_AudioToOrderMemo),
                        getString(R.string.des_SymbolsMemo)
                )

                val activityList = arrayOf(
                        AudioToOrderMemoMenuActivity::class.java,//AudioToOrderMemoMenuActivity::class.java,
                        SymbolMemoActivity::class.java
                )

                val menuPicture = arrayOf(
                        "AudioToOrder",
                        "Symbol"
                )

                val menuAudio = arrayOf(
                        "helptest",
                        "helptest"
                )

                val menuAcTitle = arrayOf<String>(
                        getString(R.string.title_AudioToOrderMemo),
                        getString(R.string.title_SymbolsMemo)
                )

                process(menuTitle, menuDes, menuPicture, menuAudio,activityList,menuAcTitle)
            }
        }
    }

    private fun process(menuTitle : Array<String>, menuDes : Array<String>, menuPicture : Array<String>, menuAudio: Array<String>, activityList: Array<Class<out AppCompatActivity>>, menuAcTitle: Array<String>)
    {
        val menuList = ArrayList<ModuleMenuItem>()

        for (i in 0..menuPicture.lastIndex) {
            menuList.add(ModuleMenuItem(menuTitle[i], menuDes[i], menuPicture[i], menuAudio[i],activityList[i],menuAcTitle[i]))
        }

        val menuAdapter = ModuleMenuListAdapter(this@MenuExercicesActivity, R.layout.module_menu_list_layout, menuList)

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