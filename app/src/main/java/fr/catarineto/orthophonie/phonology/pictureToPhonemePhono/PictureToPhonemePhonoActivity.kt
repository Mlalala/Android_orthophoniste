package fr.catarineto.orthophonie.phonology.pictureToPhonemePhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess
import fr.catarineto.orthophonie.utils.Help

class PictureToPhonemePhonoActivity : AppCompatActivity(){

    private var indexSerie : Int = 0
    private var indexInSerie : Int = 0

    private var proposals : List<String>? = null
    private var answer : Int = 0

    private var lengthSerie : Int = 0

    private var title : TextView? = null
    private var picture : ImageView? = null
    private  var picturePlayer : ImageView? = null
    private var button1 : Button? = null
    private var button2 : Button? = null

    private var media : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.picture_to_phoneme_phono_layout)

        indexSerie = intent.getIntExtra("EXTRA_POSITION",0)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()

        lengthSerie = databaseAccess.countPictureToPhonemePhono(indexSerie+1)
        proposals = databaseAccess.getProposalsPictureToPhonemePhono(indexSerie+1)

        answer = databaseAccess.getAnswerPictureToPhonemePhono(indexSerie+1, indexInSerie+1)

        title = findViewById(R.id.PictureToPhoneme_text_serie)
        picture = findViewById(R.id.PictureToPhonemeimage)
        picturePlayer = findViewById(R.id.PictureToPhonemeimagePlay)
        button1 = findViewById(R.id.PictureToPhoneme_button1)
        button2 = findViewById(R.id.PictureToPhoneme_button2)

        display(databaseAccess)
    }

    private fun display(databaseAccess : DatabaseAccess){
        title!!.text = "Série " + (indexSerie + 1).toString() + " - " + (indexInSerie + 1).toString()
        picture!!.setImageResource(resources.getIdentifier("picturetophonemephono" + (indexSerie + 1).toString() + (indexInSerie + 1).toString(), "drawable", "fr.catarineto.orthophonie"))
        button1!!.text = proposals!![0]
        button2!!.text = proposals!![1]

        media?.reset()
        media = MediaPlayer.create(this, resources.getIdentifier("picturetophonemephono"+(indexSerie+1).toString() +(indexInSerie+1).toString(),"raw","fr.catarineto.orthophonie"))
        media!!.start()

        picturePlayer!!.setOnClickListener{
            media?.reset()
            MediaPlayer.create(this, resources.getIdentifier("picturetophonemephono"+(indexSerie+1).toString() +(indexInSerie+1).toString(),"raw","fr.catarineto.orthophonie")).start()
            media!!.start()
        }

        button1!!.setOnClickListener {
            if (answer == 0){
                manageItem("CORRECT !",databaseAccess)
            }
            else{
                manageItem("FAUX !",databaseAccess)
            }
        }
        button2!!.setOnClickListener {
            if (answer == 1){
                manageItem("CORRECT !",databaseAccess)
            }
            else{
                manageItem("FAUX !",databaseAccess)
            }
        }
    }

    private fun manageItem(title : String, databaseAccess : DatabaseAccess) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        builder.setTitle(title).setView(dialogView)

        if (title == "CORRECT !"){
            if (indexInSerie < lengthSerie - 1){
                builder.setCancelable(false)
                builder.setPositiveButton("Suivant") { _, _ ->
                    indexInSerie += 1
                    answer = databaseAccess.getAnswerPictureToPhonemePhono(indexSerie+1, indexInSerie+1)
                    display(databaseAccess)
                }
            }
            else {
                builder.setCancelable(false)
                builder.setPositiveButton("Revenir au menu") { _, _ ->
                    val intent = Intent(this, PictureToPhonemePhonoMenuActivity::class.java)
                    databaseAccess.close()
                    startActivity(intent)
                    finish()
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, PictureToPhonemePhonoMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.help,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.action_help  -> Help(this@PictureToPhonemePhonoActivity, getString(R.string.help_PictureToPhonemePhono), getString(R.string.title_PictureToPhonemePhono), "helptest")
        }
        return super.onOptionsItemSelected(item)
    }
}