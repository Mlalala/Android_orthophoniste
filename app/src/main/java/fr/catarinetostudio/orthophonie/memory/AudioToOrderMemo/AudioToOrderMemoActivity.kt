package fr.catarinetostudio.orthophonie.memory.AudioToOrderMemo

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.*
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.utils.DatabaseAccess
import fr.catarinetostudio.orthophonie.utils.Help

class AudioToOrderMemoActivity : AppCompatActivity() {

    private var index_in_serie : Int = 0
    private var index_serie : Int = 0
    private var proposal : List<String>? = null
    private var answer : List<String>? = null

    private var media : MediaPlayer? = null

    private var length_serie : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_to_order_layout)

        index_serie = intent.getIntExtra("EXTRA_POSITION",0)


        //Serie lengh
        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var sql_query = databaseAccess.countAudioToOrderMemo(index_serie+1)
        length_serie = sql_query
        //databaseAccess.close()

        //init proposals, answer and audio
        init_serie_data(databaseAccess)
        //Change all the strings and audio of the current serie item
        display_phono(proposal!!, answer!!, databaseAccess)

        //Play audio one time
        media?.reset()
        media = MediaPlayer.create(this, getResources().getIdentifier("audiotowordphono"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.catarinetostudio.orthophonie"))
        media!!.start()
    }

    fun init_serie_data(databaseAccess : DatabaseAccess){
        //databaseAccess.open()
        var sql_query = databaseAccess.getAudioToOrderMemo(index_serie+1,index_in_serie+1)
        //databaseAccess.close()

        proposal = sql_query[0].toString().split("-")
        answer = sql_query[1].split("-")

    }

    fun display_phono(proposals : List<String>, answer: List<String>, databaseAccess : DatabaseAccess) {
        val textview = findViewById<TextView>(R.id.audioToOrder_serie)
        textview.text = "Série " + (index_serie + 1).toString() + " - " + (index_in_serie + 1).toString()

        val adapter = ArrayAdapter(
                this, // Context
                android.R.layout.simple_spinner_item, // Layout
                proposals // Array
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        val spinner1 = findViewById(R.id.spinner1) as Spinner
        val spinner2 = findViewById(R.id.spinner2) as Spinner
        val spinner3 = findViewById(R.id.spinner3) as Spinner

        spinner1.adapter = adapter
        spinner2.adapter = adapter
        spinner3.adapter = adapter

        val button = findViewById(R.id.memory_button) as Button
        button.text = "vérfier réponse"
        button.setOnClickListener {isCorrect(spinner1,spinner2,spinner3,answer,databaseAccess)/*;  Toast.makeText(this, "spinner  = " + spinner1.getSelectedItem().toString(), Toast.LENGTH_LONG).show()*/}



        val audio_view = findViewById<ImageView>(R.id.imagePlay)

        audio_view.setOnClickListener {
            //Toast.makeText(this, "Play", Toast.LENGTH_LONG).show()
            media?.reset()
            media = MediaPlayer.create(this, getResources().getIdentifier("audiotowordphono" + (index_serie + 1).toString() + (index_in_serie + 1).toString(), "raw", "fr.catarinetostudio.orthophonie"))
            media!!.start()
        }
    }

    fun isCorrect(spinner1 : Spinner, spinner2: Spinner, spinner3 : Spinner,response : List<String>, databaseAccess : DatabaseAccess){
        //Toast.makeText(this, "reponse =" + response, Toast.LENGTH_LONG).show()
        if ((spinner1.getSelectedItem().toString() == response[0]) && (spinner2.getSelectedItem().toString() == response[1]) && (spinner3.getSelectedItem().toString() == response[2])){
            manageItem("CORRECT !", databaseAccess)
        }

        else {
            manageItem("FAUX !", databaseAccess)
        }

    }

    fun manageItem(title : String,databaseAccess : DatabaseAccess) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        builder.setTitle(title).setView(dialogView)


        // Add the buttons
        if (title == "CORRECT !"){
            if (index_in_serie < length_serie){
                builder.setCancelable(false)
                builder.setPositiveButton("Suivant") { dialog, id ->
                    index_in_serie += 1
                    //init proposals, answer and audio
                    init_serie_data(databaseAccess)
                    //Play audio one time
                    media?.reset()
                    media = MediaPlayer.create(this, getResources().getIdentifier("audiotowordphono"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.catarinetostudio.orthophonie"))
                    media!!.start()
                    //Change all the strings and audio of the current serie item
                    display_phono(proposal!!, answer!!, databaseAccess)
                }
            }
            else {
                builder.setCancelable(false)
                builder.setPositiveButton("Revenir au menu") { dialog, id ->
                    val intent = Intent(this, AudioToOrderMemoMenuActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.help,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.action_help  -> Help(this@AudioToOrderMemoActivity, "? TODO ?", getString(R.string.title_AudioToOrderMemo), "helptest")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, AudioToOrderMemoMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }


}