package fr.catarinetostudio.orthophonie.articulation

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import fr.catarinetostudio.orthophonie.MainActivity
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.utils.DatabaseAccess
import java.io.File


class DescriptionArtiActivity : AppCompatActivity(){

    private var adapter_simple : ArrayAdapter<String>? = null
    val REQUEST_VIDEO_CAPTURE = 1
    private var indexInSerie : Int = 0
    private var indexSerie : Int = 0
    private var test1 : String = "test record"
    private var test2 : String = "test lauch video"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_list_layout)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var articulation_lettre = databaseAccess.getArticulationLetter()


        adapter_simple = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,articulation_lettre)
        var listview = findViewById<ListView>(R.id.list_menu)
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, "Position Clicked:"+" "+position, Toast.LENGTH_LONG).show()
            var intent = Intent(this, DescriptionArtiLevel::class.java)
            intent.putExtra("EXTRA_POSITION",position)
            startActivity(intent)
            finish()
        }

        val mAdView : AdView
        mAdView = this.findViewById(R.id.adViewBottom)
        val adRequestBottom = AdRequest.Builder().build()
        mAdView.loadAd(adRequestBottom)
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