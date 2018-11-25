package fr.insa_cvl.orthophonie.visual.searchSyllableVisu

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
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchSyllableVisuActivity : AppCompatActivity(){

    private var nb_correct = 0.0
    private var nb_error = 0.0
    private var true_nb_correct = 0.0

    private var index_serie = 0
    private var in_serie_size = 0

    private val size_text = 18f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var list_syllables = databaseAccess.get_SearchSyllableVisu(index_serie+1)
        val answers = databaseAccess.get_Answers_SearchSyllableVisu(index_serie+1)
        databaseAccess.close()

        in_serie_size = answers.size

        while (list_syllables.size != 32) {
            list_syllables.addAll(list_syllables)
        }

        list_syllables.shuffle()
        answers.shuffle()


        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)
        builder.setTitle("Objectif").setView(dialogView)
        builder.setMessage("\nTrouvez les \"" + answers[0] + "\"")
        val dialog = builder.create()
        dialog.show()

        nb_true_correct(answers[0],list_syllables)

        val row_param = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f        )

        val button_param = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        )
        button_param.setMargins(6,6,10,6)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0..list_syllables.size/4-1){
            var row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = row_param

            for (j in 0..3){
                var button = Button(this)
                button.setAllCaps(false)
                button.textSize = size_text
                button.text = list_syllables[j+i*4]
                button.layoutParams = button_param
                button.setBackgroundColor(getColor(R.color.memoryDefault))
                row.addView(button)

                button.setOnClickListener{
                    click_process(button,answers)
                }
            }
            table.addView(row)
        }
    }


    fun nb_true_correct(answer : String, syllables : ArrayList<String>){
        if ( true_nb_correct == 0.0){
            for (s in syllables){
                if (s.contains(answer)){
                    true_nb_correct += 1
                }
            }
        }
        else {
            true_nb_correct = syllables.size - true_nb_correct
        }
    }

    fun click_process(button : Button, answers : ArrayList<String>){
        if (button.text.contains(answers[0])){
            nb_correct += 1
            button.setBackgroundColor(getColor(R.color.memoryValid))
            button.setOnClickListener{}
            if (nb_correct == true_nb_correct){
                manageItem()
            }
        }
        else {
            nb_error += 1
            button.setBackgroundColor(getColor(R.color.memoryError))
            button.setOnClickListener{}
        }
    }

    fun manageItem() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        var score = 100.0 * (nb_correct - nb_error)/(true_nb_correct)

        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.CEILING

        builder.setTitle("Votre score est :   " + df.format(score) + "%").setView(dialogView)

        builder.setPositiveButton("Revenir au menu") { dialog, id ->
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

    fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton(getString(R.string.suggestion)) { dialog, id ->
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