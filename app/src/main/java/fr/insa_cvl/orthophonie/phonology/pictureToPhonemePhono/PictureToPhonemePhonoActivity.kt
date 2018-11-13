package fr.insa_cvl.orthophonie.phonology.pictureToPhonemePhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess
import fr.insa_cvl.orthophonie.phonology.audioToWordPhono.AudioToWordPhonoMenuActivity

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

        MediaPlayer.create(this, getResources().getIdentifier("picturetophonemephono"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie")).start()

        picturePlayer!!.setOnClickListener{
            MediaPlayer.create(this, getResources().getIdentifier("picturetophonemephono"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie")).start()
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
        val dialogView = inflater.inflate(R.layout.alert_phonology_layout, null)

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
}