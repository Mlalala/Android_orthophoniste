package fr.catarinetostudio.orthophonie.visual.searchSyllableVisu

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.utils.DatabaseAccess
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchSyllableVisuActivity : AppCompatActivity(){

    private var nbCorrect = 0.0
    private var nbError = 0.0
    private var trueNbCorrect = 0.0

    private var indexSerie = 0
    private var inSerieSize = 0

    private val sizeText = 18f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        indexSerie = intent.getIntExtra("EXTRA_POSITION",0)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val listSyllables = databaseAccess.getSearchSyllableVisu(indexSerie+1)
        val answers = databaseAccess.getAnswersSearchSyllableVisu(indexSerie+1)
        databaseAccess.close()

        inSerieSize = answers.size

        while (listSyllables.size != 32) {
            listSyllables.addAll(listSyllables)
        }

        listSyllables.shuffle()
        answers.shuffle()


        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)
        builder.setTitle("Objectif").setView(dialogView)
        builder.setMessage("\nTrouvez les \"" + answers[0] + "\"")
        val dialog = builder.create()
        dialog.show()

        nbTrueCorrect(answers[0],listSyllables)

        val rowParam = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f        )

        val buttonParam = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        )
        buttonParam.setMargins(6,6,10,6)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0 until listSyllables.size/4){
            val row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = rowParam

            for (j in 0..3){
                val button = Button(this)
                button.setAllCaps(false)
                button.textSize = sizeText
                button.text = listSyllables[j+i*4]
                button.layoutParams = buttonParam
                button.setBackgroundColor(getColor(R.color.memoryDefault))
                row.addView(button)

                button.setOnClickListener{
                    clickProcess(button,answers)
                }
            }
            table.addView(row)
        }
    }


    private fun nbTrueCorrect(answer : String, syllables : ArrayList<String>){
        if ( trueNbCorrect == 0.0){
            for (s in syllables){
                if (s.contains(answer)){
                    trueNbCorrect += 1
                }
            }
        }
        else {
            trueNbCorrect = syllables.size - trueNbCorrect
        }
    }

    private fun clickProcess(button : Button, answers : ArrayList<String>){
        if (button.text.contains(answers[0])){
            nbCorrect += 1
            button.setBackgroundColor(getColor(R.color.memoryValid))
            button.setOnClickListener{}
            if (nbCorrect == trueNbCorrect){
                manageItem()
            }
        }
        else {
            nbError += 1
            button.setBackgroundColor(getColor(R.color.memoryError))
            button.setOnClickListener{}
        }
    }

    private fun manageItem() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        val score = 100.0 * (nbCorrect - nbError)/(trueNbCorrect)

        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.CEILING

        builder.setTitle("Votre score est :   " + df.format(score) + "%").setView(dialogView)

        builder.setPositiveButton("Revenir au menu") { _, _ ->
            val intent = Intent(this, SearchSyllableVisuMenuActivity::class.java)
            startActivity(intent)
            finish()
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
            R.id.action_help  -> manageMenu(getString(R.string.help),getString(R.string.help_SearchSyllableVisu))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton(getString(R.string.suggestion)) { _, _ ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getString(R.string.email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour l'activitée " + getString(R.string.title_SearchSyllableVisu) +" de l'Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, SearchSyllableVisuMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
}