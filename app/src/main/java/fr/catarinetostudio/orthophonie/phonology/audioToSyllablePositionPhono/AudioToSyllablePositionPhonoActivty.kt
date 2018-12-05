package fr.catarinetostudio.orthophonie.phonology.audioToSyllablePositionPhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.utils.DatabaseAccess

class AudioToSyllablePositionPhonoActivty  : AppCompatActivity() {

    private var indexInSerie : Int = 0
    private var indexSerie : Int = 0
    private var proposal : List<String>? = null
    private var answer : Int = 0
    private var mot : String = ""

    private var media : MediaPlayer? = null

    private var lengthSerie : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_syllable_position_layout)

        indexSerie = intent.getIntExtra("EXTRA_POSITION",0)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val sqlQuery = databaseAccess.countAudioToSyllabePosition(indexSerie+1)
        lengthSerie = sqlQuery

        initSerieData(databaseAccess)
        displayPhono(proposal!!, answer, databaseAccess)

        media?.reset()
        media = MediaPlayer.create(this, resources.getIdentifier("audiotowordphono"+(indexSerie+1).toString() +(indexInSerie+1).toString(),"raw","fr.catarinetostudio.orthophonie"))
        media!!.start()
    }

    private fun initSerieData(databaseAccess : DatabaseAccess){
        val sqlQuery = databaseAccess.getAudioToSyllabePosition(indexSerie+1,indexInSerie+1)

        proposal = sqlQuery[0].split("-")
        answer = sqlQuery[1].toInt()
        mot=sqlQuery[2]
    }

    private fun displayPhono(proposals : List<String>, answer: Int, databaseAccess : DatabaseAccess) {
        val textview = findViewById<TextView>(R.id.phono_text_serie)
        textview.text = "Série " + (indexSerie + 1).toString() + " - " + (indexInSerie + 1).toString()

        val texviewMot = findViewById<TextView>(R.id.phono_syllabe)
        texviewMot.text = "Où se trouve le " + mot +" ?"

        val button1 = findViewById<Button>(R.id.phono_button1)
        button1.text = proposals[0]
        button1.setOnClickListener {isCorrect(answer,0, databaseAccess)}

        val button2 = findViewById<Button>(R.id.phono_button2)
        button2.text = proposals[1]
        button2.setOnClickListener {isCorrect(answer,1, databaseAccess)}

        val button3 = findViewById<Button>(R.id.phono_button3)
        button3.text = proposals[2]
        button3.setOnClickListener {isCorrect(answer,2, databaseAccess)}

        val audioView = findViewById<ImageView>(R.id.imagePlay)

        audioView.setOnClickListener {
            media?.reset()
            media = MediaPlayer.create(this, resources.getIdentifier("audiotowordphono" + (indexSerie + 1).toString() + (indexInSerie + 1).toString(), "raw", "fr.catarinetostudio.orthophonie"))
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
            if (indexInSerie < lengthSerie){
                builder.setCancelable(false)
                builder.setPositiveButton("Suivant") { _, _ ->
                    indexInSerie += 1
                    initSerieData(databaseAccess)

                    media?.reset()
                    media = MediaPlayer.create(this, resources.getIdentifier("audiotowordphono"+(indexSerie+1).toString() +(indexInSerie+1).toString(),"raw","fr.catarinetostudio.orthophonie"))
                    media!!.start()

                    displayPhono(proposal!!, answer, databaseAccess)
                }
            }
            else {
                builder.setCancelable(false)
                builder.setPositiveButton("Revenir au menu") { _, _ ->
                    val intent = Intent(this, AudioToSyllablePositionPhonoMenuActivity::class.java)
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
            R.id.action_help  -> manageMenu(getString(R.string.help),getString(R.string.help_AudioTosyllable))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton("Suggestion") { _, _ ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "lamontagnettestudio@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour l'activitée " + getString(R.string.title_AudioToRhyme) +" de l'Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, AudioToSyllablePositionPhonoMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }

}