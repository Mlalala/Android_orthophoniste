package fr.insa_cvl.orthophonie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.view.menu.MenuAdapter
import android.widget.Toast
import android.widget.TextView
import android.widget.LinearLayout




class MainActivity : AppCompatActivity() {

    private val main_menu_title = arrayOf(
            R.string.title_arti,
            R.string.title_phono,
            R.string.title_visu
    )

    private val main_menu_logo = arrayOf(
            R.drawable.logo_arti,
            R.drawable.logo_phono,
            R.drawable.logovisu
    )

    //destinationActivity::class.java

    private val activity_list = arrayOf(
            ArticulationActivity::class.java,
            PhonologyActivity::class.java,
            VisualActivity::class.java
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
                    Toast.makeText(this, "Position Clicked:"+" "+position,Toast.LENGTH_LONG).show()
                    val intent = Intent(this,activity_list[position])
                    startActivity(intent)
                    finish()
                }

            }

}


