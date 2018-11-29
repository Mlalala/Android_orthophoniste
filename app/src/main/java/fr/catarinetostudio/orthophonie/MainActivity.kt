package fr.catarinetostudio.orthophonie

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import fr.catarinetostudio.orthophonie.articulation.DescriptionArtiActivity
import fr.catarinetostudio.orthophonie.memory.MemoryMenuActivity
import fr.catarinetostudio.orthophonie.phonology.PhonologyMenuActivity
import fr.catarinetostudio.orthophonie.visual.VisualMenuActivity



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
            R.drawable.visu,
            R.drawable.memory
    )

    private val activity_list = arrayOf(
            DescriptionArtiActivity::class.java,
            PhonologyMenuActivity::class.java,
            VisualMenuActivity::class.java,
            MemoryMenuActivity::class.java
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuList = ArrayList<MenuItem>()

        for (i in 0..main_menu_logo.lastIndex) {
            menuList.add(MenuItem(main_menu_title[i], main_menu_logo[i]))
        }

        val menuAdapter = MenuListAdapter(this, R.layout.activity_main_menu_list_layout, menuList)

        var list = findViewById(R.id.menu_list) as ListView

        list.adapter = menuAdapter

        list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,activity_list[position])
            startActivity(intent)
            finish()
        }

        Ads(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.action_settings -> Toast.makeText(this, "Settings",Toast.LENGTH_LONG).show()
            R.id.action_aboutus  -> manageMenu(getString(R.string.aboutus),getString(R.string.aboutus_content))

        }
        return super.onOptionsItemSelected(item)
    }

    fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_picture_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton("Contactez-nous") { dialog, id ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }
}


