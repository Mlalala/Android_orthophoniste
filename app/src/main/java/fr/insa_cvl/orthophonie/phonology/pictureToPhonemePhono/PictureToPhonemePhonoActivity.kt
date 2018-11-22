package fr.insa_cvl.orthophonie.phonology.pictureToPhonemePhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess

class PictureToPhonemePhonoActivity : AppCompatActivity(){

    private var index_serie : Int = 0
    private var index_in_serie : Int = 0

    private var proposals : List<String>? = null
    private var answer : Int = 0

    private var length_serie : Int = 0

    private var title : TextView? = null
    private var picture : ImageView? = null
    private  var picturePlayer : ImageView? = null
    private var button1 : Button? = null
    private var button2 : Button? = null

    private var media : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.picture_to_phoneme_phono_layout)

        index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()

        length_serie = databaseAccess.count_PictureToPhonemePhono(index_serie+1)
        proposals = databaseAccess.get_proposals_PictureToPhonemePhono(index_serie+1)

        answer = databaseAccess.get_answer_PictureToPhonemePhono(index_serie+1, index_in_serie+1)

        title = findViewById(R.id.PictureToPhoneme_text_serie)
        picture = findViewById(R.id.PictureToPhonemeimage)
        picturePlayer = findViewById(R.id.PictureToPhonemeimagePlay)
        button1 = findViewById(R.id.PictureToPhoneme_button1)
        button2 = findViewById(R.id.PictureToPhoneme_button2)

        display(databaseAccess)
    }

    fun display(databaseAccess : DatabaseAccess){
        title!!.text = "Série " + (index_serie + 1).toString() + " - " + (index_in_serie + 1).toString()
        picture!!.setImageResource(getResources().getIdentifier("picturetophonemephono" + (index_serie + 1).toString() + (index_in_serie + 1).toString(), "drawable", "fr.insa_cvl.orthophonie"))
        button1!!.text = proposals!!.get(0)
        button2!!.text = proposals!!.get(1)

        media?.reset()
        media = MediaPlayer.create(this, getResources().getIdentifier("picturetophonemephono"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie"))
        media!!.start()

        picturePlayer!!.setOnClickListener{
            media?.reset()
            MediaPlayer.create(this, getResources().getIdentifier("picturetophonemephono"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie")).start()
            media!!.start()
        }

        button1!!.setOnClickListener {
            if (answer == 0){
                //Toast.makeText(this,"Correct", Toast.LENGTH_LONG).show()
                manageItem("CORRECT !",databaseAccess)
            }
            else{
                //Toast.makeText(this,"Incorrect", Toast.LENGTH_LONG).show()
                manageItem("FAUX !",databaseAccess)
            }
        }
        button2!!.setOnClickListener {
            if (answer == 1){
                //Toast.makeText(this,"Correct", Toast.LENGTH_LONG).show()
                manageItem("CORRECT !",databaseAccess)
            }
            else{
                //Toast.makeText(this,"Incorrect", Toast.LENGTH_LONG).show()
                manageItem("FAUX !",databaseAccess)
            }
        }

    }

    fun manageItem(title : String,databaseAccess : DatabaseAccess) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        builder.setTitle(title).setView(dialogView)

        // Add the buttons
        if (title == "CORRECT !"){
            if (index_in_serie < length_serie - 1){
                builder.setCancelable(false)
                builder.setPositiveButton("Suivant") { dialog, id ->
                    index_in_serie += 1
                    answer = databaseAccess.get_answer_PictureToPhonemePhono(index_serie+1, index_in_serie+1)
                    display(databaseAccess)
                }
            }
            else {
                builder.setCancelable(false)
                builder.setPositiveButton("Revenir au menu") { dialog, id ->
                    val intent = Intent(this, PictureToPhonemePhonoMenuActivity::class.java)
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
            R.id.action_help  -> manageMenu(getString(R.string.help),getString(R.string.help_PictureToPhonemePhono))
        }
        return super.onOptionsItemSelected(item)
    }

    fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton(getString(R.string.suggestion)) { dialog, id ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour l'activitée " + getString(R.string.title_PictureToPhonemePhono) +" de l'Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }
}