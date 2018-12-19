package fr.catarineto.orthophonie.phonology.audioToWordPhono

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.*
import android.media.MediaPlayer
import android.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import fr.catarineto.orthophonie.utils.DatabaseAccess
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.Help


class AudioToWordPhonoActivity : AppCompatActivity(){

    private var indexInSerie : Int = 0
    private var indexSerie : Int = 0
    private var proposal : List<String>? = null
    private var answer : Int = 0

    private var media : MediaPlayer? = null

    private var lengthSerie : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_to_word_phono_layout)

        indexSerie = intent.getIntExtra("EXTRA_POSITION",0)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val sqlQuery = databaseAccess.countAudioToWordPhono(indexSerie+1)
        lengthSerie = sqlQuery

        initDerieData(databaseAccess)
        displayPhono(proposal!!, answer, databaseAccess)

        media?.reset()
        media = MediaPlayer.create(this, resources.getIdentifier("audiotowordphono"+(indexSerie+1).toString() +(indexInSerie+1).toString(),"raw","fr.catarineto.orthophonie"))
        media!!.start()
    }

    private fun initDerieData(databaseAccess : DatabaseAccess){
        val sqlQuery = databaseAccess.getAudioToWordPhono(indexSerie+1,indexInSerie+1)

        proposal = sqlQuery[0].split("-")
        answer = sqlQuery[1].toInt()
    }

    private fun displayPhono(proposals : List<String>, answer: Int, databaseAccess : DatabaseAccess) {
        val textview = findViewById<TextView>(R.id.phono_text_serie)
        textview.text = "Série " + (indexSerie + 1).toString() + " - " + (indexInSerie + 1).toString()

        val button1 = findViewById<Button>(R.id.phono_button1)
        button1.text = proposals[0]
        button1.setOnClickListener {isCorrect(answer,0, databaseAccess)}

        val button2 = findViewById<Button>(R.id.phono_button2)
        button2.text = proposals[1]
        button2.setOnClickListener {isCorrect(answer,1, databaseAccess)}

        val audioView = findViewById<ImageView>(R.id.imagePlay)

        audioView.setOnClickListener {
            media?.reset()
            media = MediaPlayer.create(this, resources.getIdentifier("audiotowordphono" + (indexSerie + 1).toString() + (indexInSerie + 1).toString(), "raw", "fr.catarineto.orthophonie"))
            media!!.start()
        }
    }

    private fun isCorrect(answer : Int, response : Int, databaseAccess : DatabaseAccess){
        if (answer == response) {
            manageItem("CORRECT !", databaseAccess)
        }
        else{
            manageItem("FAUX !", databaseAccess)
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
                    initDerieData(databaseAccess)
                    media?.reset()
                    media = MediaPlayer.create(this, resources.getIdentifier("audiotowordphono"+(indexSerie+1).toString() +(indexInSerie+1).toString(),"raw","fr.catarineto.orthophonie"))
                    media!!.start()
                    displayPhono(proposal!!, answer, databaseAccess)
                }
            }
            else {
                builder.setCancelable(false)
                builder.setPositiveButton("Revenir au menu") { _, _ ->
                    val intent = Intent(this, AudioToWordPhonoMenuActivity::class.java)
                    databaseAccess.close()
                    startActivity(intent)
                    finish()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.help,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.action_help  -> Help(this@AudioToWordPhonoActivity, getString(R.string.help_AudioToWordPhono), getString(R.string.title_AudioToWordPhono), "helptest")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, AudioToWordPhonoMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
}