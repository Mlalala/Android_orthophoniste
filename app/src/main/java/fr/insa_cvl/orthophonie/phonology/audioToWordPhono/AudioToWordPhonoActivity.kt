package fr.insa_cvl.orthophonie.phonology.audioToWordPhono

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.*
import android.media.MediaPlayer
import android.app.AlertDialog
import android.widget.TextView
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess
import fr.insa_cvl.orthophonie.R


class AudioToWordPhonoActivity : AppCompatActivity(){

    private var index_in_serie : Int = 0
    private var index_serie : Int = 0
    private var proposal : List<String>? = null
    private var answer : Int = 0

    private var length_serie : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_to_word_phono_layout)

        index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        //Serie lengh
        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var sql_query = databaseAccess.count_AudioToWordPhono(index_serie+1)
        length_serie = sql_query
        //databaseAccess.close()

        //init proposals, answer and audio
        init_serie_data(databaseAccess)
        //Change all the strings and audio of the current serie item
        display_phono(proposal!!, answer, databaseAccess)

        //Play audio one time
        MediaPlayer.create(this, getResources().getIdentifier("audio"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie")).start()

    }

    fun init_serie_data(databaseAccess : DatabaseAccess){
        //databaseAccess.open()
        var sql_query = databaseAccess.get_AudioToWordPhono(index_serie+1,index_in_serie+1)
        //databaseAccess.close()

        proposal = sql_query[0].toString().split("-")
        answer = sql_query[1].toInt()
    }

    fun display_phono(proposals : List<String>, answer: Int, databaseAccess : DatabaseAccess) {
        val textview = findViewById<TextView>(R.id.phono_text_serie)
        textview.text = "Série " + (index_serie + 1).toString() + " - " + (index_in_serie + 1).toString()

        val button1 = findViewById(R.id.phono_button1) as Button
        button1.text = proposals[0]
        button1.setOnClickListener {isCorrect(answer,0, databaseAccess)}

        val button2 = findViewById(R.id.phono_button2) as Button
        button2.text = proposals[1]
        button2.setOnClickListener {isCorrect(answer,1, databaseAccess)}

        val audio_view = findViewById<ImageView>(R.id.imagePlay)

        audio_view.setOnClickListener {
            //Toast.makeText(this, "Play", Toast.LENGTH_LONG).show()
            MediaPlayer.create(this, getResources().getIdentifier("audio" + (index_serie + 1).toString() + (index_in_serie + 1).toString(), "raw", "fr.insa_cvl.orthophonie")).start()
        }
    }

    fun isCorrect(answer : Int, response : Int, databaseAccess : DatabaseAccess){
        if (answer == response) {
            manageItem("CORRECT !", databaseAccess)
        }
        else{
            manageItem("FAUX !", databaseAccess)
        }
    }

    fun manageItem(title : String,databaseAccess : DatabaseAccess) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_phonology_layout, null)

        builder.setTitle(title).setView(dialogView)


        // Add the buttons
        if (title == "CORRECT !"){
            if (index_in_serie < length_serie - 1){
                builder.setPositiveButton("Suivant") { dialog, id ->
                    index_in_serie += 1
                    //init proposals, answer and audio
                    init_serie_data(databaseAccess)
                    //Play audio one time
                    MediaPlayer.create(this, getResources().getIdentifier("audio"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie")).start()
                    //Change all the strings and audio of the current serie item
                    display_phono(proposal!!, answer, databaseAccess)
                }
            }
            else {
                builder.setPositiveButton("Revenir au menu") { dialog, id ->
                    val intent = Intent(this, AudioToWordPhonoMenuActivity::class.java)
                    databaseAccess.close()
                    startActivity(intent)
                    finish()
                }
            }
        }

        // Create the AlertDialog
        val dialog = builder.create()
        dialog.show()
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