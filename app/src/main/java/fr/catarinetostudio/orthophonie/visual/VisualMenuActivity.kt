package fr.catarinetostudio.orthophonie.visual

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
import fr.catarinetostudio.orthophonie.visual.memorySyllabesVisu.MemorySyllabesVisuMenuActivity
import fr.catarinetostudio.orthophonie.visual.searchSyllableVisu.SearchSyllableVisuMenuActivity

class VisualMenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        val menutitle = arrayOf(
                getString(R.string.title_MemorySyllablesVisu),
                getString(R.string.title_SearchSyllableVisu)
        )

        val menudes = arrayOf(
                getString(R.string.des_MemorySyllablesVisu),
                getString(R.string.des_SearchSyllableVisu)
        )

        val menupicture = arrayOf(
                "MemorySyllablesVisu",
                "SearchSyllableVisu"
        )

        val menuaudio = arrayOf(
                "helptest",
                "helptest"
        )

        val activitylist = arrayOf(
                MemorySyllabesVisuMenuActivity::class.java,
                SearchSyllableVisuMenuActivity::class.java
        )

        val menuAcTitle = arrayOf(
                getString(R.string.title_MemorySyllablesVisu),
                getString(R.string.title_SearchSyllableVisu)
        )

        val menuList = ArrayList<ModuleMenuItem>()

        for (i in 0..menupicture.lastIndex) {
            menuList.add(ModuleMenuItem(menutitle[i], menudes[i], menupicture[i], menuaudio[i],activitylist[i],menuAcTitle[i]))
        }

        val menuAdapter = ModuleMenuListAdapter(this@VisualMenuActivity, R.layout.module_menu_list_layout, menuList)

        val list = findViewById<ListView>(R.id.list_menu)

        list.adapter = menuAdapter


       list.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            val intent = Intent(this, activitylist[position])
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