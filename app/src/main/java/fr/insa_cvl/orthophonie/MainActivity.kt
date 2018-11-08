package fr.insa_cvl.orthophonie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import fr.insa_cvl.orthophonie.articulation.DescriptionArtiActivity
import fr.insa_cvl.orthophonie.memory.MemoryMenuActivity
import fr.insa_cvl.orthophonie.phonology.PhonologyMenuActivity
import fr.insa_cvl.orthophonie.visual.VisualActivity


class MainActivity : AppCompatActivity() {

    private val main_menu_title = arrayOf(
            R.string.title_arti,
            R.string.title_phono,
            R.string.title_visu,
            R.string.title_memo
    )

    private val main_menu_logo = arrayOf(
            R.drawable.arti,
            R.drawable.phono,
            R.drawable.logovisu,
            R.drawable.logovisu
    )

    private val activity_list = arrayOf(
            DescriptionArtiActivity::class.java,
            PhonologyMenuActivity::class.java,
            VisualActivity::class.java,
            MemoryMenuActivity::class.java
    )


    override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                val menuList = ArrayList<MenuItem>()

                for (i in 0..main_menu_logo.lastIndex) {
                    menuList.add(MenuItem(main_menu_title[i], main_menu_logo[i]))
                }

                val menuAdapter = MenuListAdapter(this, R.layout.menu_list_layout, menuList)

                var list = findViewById(R.id.menu_list) as ListView

                list.adapter = menuAdapter



                list.setOnItemClickListener { parent, view, position, id ->
                    //Toast.makeText(this, "Position Clicked:"+" "+position,Toast.LENGTH_LONG).show()
                    val intent = Intent(this,activity_list[position])
                    startActivity(intent)
                    finish()
                }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
}


