package fr.catarinetostudio.orthophonie.visual

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.ListView
import fr.catarinetostudio.orthophonie.Ads
import fr.catarinetostudio.orthophonie.MainActivity
import fr.catarinetostudio.orthophonie.ModuleMenuItem
import fr.catarinetostudio.orthophonie.ModuleMenuListAdapter
import fr.catarinetostudio.orthophonie.*
import fr.catarinetostudio.orthophonie.visual.memorySyllabesVisu.MemorySyllabesVisuMenuActivity
import fr.catarinetostudio.orthophonie.visual.searchSyllableVisu.SearchSyllableVisuMenuActivity

class VisualMenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        Ads(this)

        val menu_title = arrayOf(
                getString(R.string.title_MemorySyllablesVisu),
                getString(R.string.title_SearchSyllableVisu)
        )

        val menu_des = arrayOf(
                getString(R.string.des_MemorySyllablesVisu),
                getString(R.string.des_MemorySyllablesVisu)
        )

        val menu_picture = arrayOf(
                "MemorySyllablesVisu",
                "SearchSyllableVisu"
        )

        val activity_list = arrayOf(
                MemorySyllabesVisuMenuActivity::class.java,
                SearchSyllableVisuMenuActivity::class.java
        )

        val menuList = ArrayList<ModuleMenuItem>()

        for (i in 0..menu_picture.lastIndex) {
            menuList.add(ModuleMenuItem(menu_title[i], menu_des[i], menu_picture[i], activity_list[i]))
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