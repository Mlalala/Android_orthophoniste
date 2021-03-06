package fr.catarineto.orthophonie

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import fr.catarineto.orthophonie.utils.MainMenuItem
import fr.catarineto.orthophonie.utils.MainMenuListAdapter
import fr.catarineto.orthophonie.utils.Ads

class MainActivity : AppCompatActivity() {

    private val mainmenutitle = arrayOf(
            R.string.title_arti,
            R.string.title_phono,
            R.string.title_visu,
            R.string.title_memo
    )

    private val mainmenulogo = arrayOf(
            R.drawable.arti,
            R.drawable.phono,
            R.drawable.visu,
            R.drawable.memory
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Ads(this@MainActivity)

        val menuList = ArrayList<MainMenuItem>()

        for (i in 0..mainmenulogo.lastIndex) {
            menuList.add(MainMenuItem(mainmenutitle[i], mainmenulogo[i]))
        }

        val menuAdapter = MainMenuListAdapter(this, R.layout.activity_main_menu_list_layout, menuList)

        val list = findViewById<ListView>(R.id.menu_list)

        list.adapter = menuAdapter

        list.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this,MenuExercicesActivity::class.java)
            intent.putExtra("EXTRA_POSITION",position)
            startActivity(intent)
            finish()
        }
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

    private fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_picture_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton("Contactez-nous") { _, _ ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }
}