package fr.catarinetostudio.orthophonie

import android.app.AlertDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import fr.catarinetostudio.orthophonie.articulation.DescriptionArtiActivity
import fr.catarinetostudio.orthophonie.memory.MemoryMenuActivity
import fr.catarinetostudio.orthophonie.phonology.PhonologyMenuActivity
import fr.catarinetostudio.orthophonie.utils.MainMenuItem
import fr.catarinetostudio.orthophonie.utils.MainMenuListAdapter
import fr.catarinetostudio.orthophonie.visual.VisualMenuActivity
import fr.catarinetostudio.orthophonie.utils.Ads


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

    private val activitylist = arrayOf(
            MainActivity::class.java, //DescriptionArtiActivity::class.java,
            PhonologyMenuActivity::class.java,
            VisualMenuActivity::class.java,
            MemoryMenuActivity::class.java
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
            val intent = Intent(this,activitylist[position])
            startActivity(intent)
            finish()
        }

        /*var mAdView : AdView

        mAdView = this.findViewById(R.id.adViewTop)
        val adRequestTop = AdRequest.Builder().build()
        mAdView.loadAd(adRequestTop)


        mAdView = this.findViewById(R.id.adViewBottom)
        val adRequestBottom = AdRequest.Builder().build()
        mAdView.loadAd(adRequestBottom)*/

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


