package fr.insa_cvl.orthophonie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.*
import android.media.MediaPlayer


class PhonologyActivity : AppCompatActivity(){

    private var index_in_serie : Int = 0
    private var index_serie : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phonology_layout)
        index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        val proposal : List<String>
        val answer : Int

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val sql_query = databaseAccess.get_serie(index_serie+1,index_in_serie+1)
        databaseAccess.close()

        proposal = sql_query[0].toString().split("-")
        answer = sql_query[1].toInt()

        //Play audio one time
        MediaPlayer.create(this, getResources().getIdentifier("audio"+(index_serie+1).toString() +(index_in_serie+1).toString(),"raw","fr.insa_cvl.orthophonie")).start()
        //Change all the strings and audio of the current serie item
        display_phono(proposal, answer)


    }

    fun display_phono(proposals : List<String>, answer: Int) {
        val textview = findViewById<TextView>(R.id.phono_text_serie)
        textview.text = "Série " + (index_serie + 1).toString() + " - " + (index_in_serie + 1).toString()

        val button1 = findViewById(R.id.phono_button1) as Button
        button1.text = proposals[0]
        button1.setOnClickListener {isCorrect(answer,0)}

        val button2 = findViewById(R.id.phono_button2) as Button
        button2.text = proposals[1]
        button2.setOnClickListener {isCorrect(answer,1)}

        val button3 = findViewById(R.id.phono_button3) as Button
        button3.text = proposals[2]
        button3.setOnClickListener {isCorrect(answer,2)}

        val button4 = findViewById(R.id.phono_button4) as Button
        button4.text = proposals[3]
        button4.setOnClickListener {isCorrect(answer,3)}

        val audio_view = findViewById<ImageView>(R.id.imagePlay)

        audio_view.setOnClickListener {
            //Toast.makeText(this, "Play", Toast.LENGTH_LONG).show()
            MediaPlayer.create(this, getResources().getIdentifier("audio" + (index_serie + 1).toString() + (index_in_serie + 1).toString(), "raw", "fr.insa_cvl.orthophonie")).start()
        }
    }

    fun isCorrect(answer : Int, response : Int){
        if (answer == response) {
            Toast.makeText(this, "Correct : A = " + answer.toString() + "| R = " + response.toString() , Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "False : A = " + answer.toString() + "| R = " + response.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this,PhonologyMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }


}