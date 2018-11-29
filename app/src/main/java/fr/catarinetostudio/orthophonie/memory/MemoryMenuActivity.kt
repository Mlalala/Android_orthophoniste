package fr.catarinetostudio.orthophonie.memory

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.ListView
import fr.catarinetostudio.orthophonie.MainActivity
import fr.catarinetostudio.orthophonie.ModuleMenuItem
import fr.catarinetostudio.orthophonie.ModuleMenuListAdapter
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.memory.AudioToOrderMemo.AudioToOrderMemoMenuActivity

class MemoryMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        val menu_title = arrayOf<String>(
                getString(R.string.title_AudioToOrderMemo),
                getString(R.string.title_SymbolsMemo)
        )

        val menu_des = arrayOf<String>(
                getString(R.string.des_AudioToOrderMemo),
                getString(R.string.des_SymbolsMemo)
        )

        val activity_list = arrayOf(
                AudioToOrderMemoMenuActivity::class.java,
                MainActivity::class.java
        )

        val menu_picture = arrayOf(
                "sample",
                "sample"
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