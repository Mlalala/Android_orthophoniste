package fr.catarinetostudio.orthophonie.memory

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.AdapterView
import android.widget.ListView
import fr.catarinetostudio.orthophonie.*
import fr.catarinetostudio.orthophonie.memory.AudioToOrderMemo.AudioToOrderMemoMenuActivity
import fr.catarinetostudio.orthophonie.utils.Ads
import fr.catarinetostudio.orthophonie.utils.ModuleMenuItem
import fr.catarinetostudio.orthophonie.utils.ModuleMenuListAdapter

class MemoryMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        val menuTitle = arrayOf<String>(
                getString(R.string.title_AudioToOrderMemo),
                getString(R.string.title_SymbolsMemo)
        )

        val menuDes = arrayOf<String>(
                getString(R.string.des_AudioToOrderMemo),
                getString(R.string.des_SymbolsMemo)
        )

        val activityList = arrayOf(
                MainActivity::class.java,//AudioToOrderMemoMenuActivity::class.java,
                MainActivity::class.java
        )

        val menuPicture = arrayOf(
                "sample",
                "sample"
        )

        val menuAudio = arrayOf(
                "helptest",
                "helptest"
        )

        val menuAcTitle = arrayOf<String>(
                getString(R.string.title_AudioToOrderMemo),
                getString(R.string.title_SymbolsMemo)
        )


        val menuList = ArrayList<ModuleMenuItem>()

        for (i in 0..menuPicture.lastIndex) {
            menuList.add(ModuleMenuItem(menuTitle[i], menuDes[i], menuPicture[i], menuAudio[i], activityList[i],menuAcTitle[i]))
        }

        val menuAdapter = ModuleMenuListAdapter(this@MemoryMenuActivity, R.layout.module_menu_list_layout, menuList)

        val list = findViewById<ListView>(R.id.list_menu)

        list.adapter = menuAdapter


        list.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            val intent = Intent(this, activityList[position])
            startActivity(intent)
            finish()
        }

        Ads(this)
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