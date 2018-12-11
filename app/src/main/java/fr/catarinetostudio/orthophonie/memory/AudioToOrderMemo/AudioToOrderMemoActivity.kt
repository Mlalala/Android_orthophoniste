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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.utils.DatabaseAccess
import fr.catarinetostudio.orthophonie.utils.Help

class AudioToOrderMemoActivity : AppCompatActivity() {

    private var index_in_serie : Int = 0
    private var index_serie : Int = 0
    private var proposal : List<String>? = null
    private var answer : List<String>? = null
    private var mot : String = ""

    private var media : MediaPlayer? = null

    private var length_serie : Int = 0
    private var serie_size : Int =  0
    private var selected = ArrayList<Int>()
    private var buttonlist = ArrayList<Button>()
    private val size_text = 18f
    private var nb_correct = 0

    private var list_answer = listOf<String>()
    private var element_0 : String = "0"
    private var element_1 : String = "1"
    private var element_2 : String = "2"

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

        val button1 = findViewById(R.id.memory_button1) as Button
        button1.text = proposals[0]
        button1.setOnClickListener {list_answer+=element_0; Toast.makeText(this, "size  = " + list_answer.size, Toast.LENGTH_LONG).show(); button1.setBackgroundColor(getColor(R.color.memorySelected))}
        //button1.setBackgroundColor(getColor(R.color.memoryDefault))


        val button2 = findViewById(R.id.memory_button2) as Button
        button2.text = proposals[1]
        button2.setOnClickListener {list_answer+=(element_1)}

        val button3 = findViewById(R.id.memory_button3) as Button
        button3.text = proposals[2]
        button3.setOnClickListener {list_answer+=(element_2)}

        val button4 = findViewById(R.id.memory_button4) as Button
        button4.text = "vérfier réponse"
        button4.setOnClickListener {isCorrect(answer,databaseAccess); button1.setBackgroundColor(getColor(R.color.memoryDefault))}



        val audio_view = findViewById<ImageView>(R.id.imagePlay)

        audio_view.setOnClickListener {
            //Toast.makeText(this, "Play", Toast.LENGTH_LONG).show()
            media?.reset()
            media = MediaPlayer.create(this, getResources().getIdentifier("audiotowordphono" + (index_serie + 1).toString() + (index_in_serie + 1).toString(), "raw", "fr.catarinetostudio.orthophonie"))
            media!!.start()
        }
    }

    fun isCorrect(response : List<String>, databaseAccess : DatabaseAccess){
        Toast.makeText(this, "anwer  = " + list_answer + "reponse =" + response, Toast.LENGTH_LONG).show()
        if (list_answer == response){
            manageItem("CORRECT !", databaseAccess)
        }

        list_answer = listOf()




        //manageItem("CORRECT !", databaseAccess)


        /*
        if (answer == response) {
            manageItem("CORRECT !", databaseAccess)
        }
        else{
            manageItem("FAUX !", databaseAccess)
        }
        */
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