package fr.insa_cvl.orthophonie.visual.memorySyllabesVisu

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.*
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess

class MemorySyllabesVisuActivity : AppCompatActivity() {
    private var selected = ArrayList<Int>()
    private var buttonlist = ArrayList<Button>()
    private var nb_correct = 0
    private  var serie_size = 0

    private val size_text = 18f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        val index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var list_elements = databaseAccess.get_MemorySyllabesVisu(index_serie+1)
        databaseAccess.close()
        serie_size = list_elements.size
        list_elements.addAll(list_elements)
        list_elements.shuffle()

        val row_param = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f        )

        val button_param = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        )
        button_param.setMargins(10,6,10,6)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0..list_elements.size/2-1){
            var row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = row_param

            for (j in 0..1){
                var button = Button(this)
                button.setAllCaps(false)
                button.textSize = 0f
                button.text = list_elements[j+i*2]
                button.layoutParams = button_param
                button.setBackgroundColor(getColor(R.color.memoryDefault))
                row.addView(button)
                buttonlist.add(button)

                button.setOnClickListener(){
                    selected.add(j+i*2)
                    click_process()
                }
            }
            table.addView(row)
        }
    }

    fun click_process(){
        if (selected.size == 1){
            buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memorySelected))
            buttonlist[selected[0]].textSize = size_text
        }
        else {
            buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memorySelected))
            buttonlist[selected[1]].textSize = size_text

            if (selected[0] != selected[1] && buttonlist[selected[0]].text == buttonlist[selected[1]].text){
                buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonlist[selected[0]].setOnClickListener(null)
                buttonlist[selected[1]].setOnClickListener(null)
                selected.clear()

                nb_correct += 1
                if (nb_correct == serie_size){
                    manageItem()
                }
            }
            else {
                this@MemorySyllabesVisuActivity.buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryError))
                this@MemorySyllabesVisuActivity.buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryError))

                Thread(Runnable {
                    this@MemorySyllabesVisuActivity.runOnUiThread(java.lang.Runnable {
                        Thread.sleep(750)
                        this@MemorySyllabesVisuActivity.buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryDefault))
                        this@MemorySyllabesVisuActivity.buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryDefault))
                        buttonlist[selected[0]].textSize = 0f
                        buttonlist[selected[1]].textSize = 0f
                        this@MemorySyllabesVisuActivity.selected.clear()
                    })
                }).start()
            }
        }
    }


    fun manageItem() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        builder.setTitle("BRAVO !").setView(dialogView)

        // Add the button
        builder.setPositiveButton("Revenir au menu") { dialog, id ->
            val intent = Intent(this, MemorySyllabesVisuMenuActivity::class.java)
            startActivity(intent)
            finish()
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
            R.id.action_help  -> manageMenu(getString(R.string.help),getString(R.string.help_MemorySyllablesVisu))
        }
        return super.onOptionsItemSelected(item)
    }

    fun manageMenu(title : String, text : String) {
        val builder = AlertDialog.Builder(this)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_layout, null)

        builder.setTitle(title).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton("Suggestion") { dialog, id ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "lamontagnettestudio@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour l'activitée " + getString(R.string.title_MemorySyllablesVisu) +" de l'Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MemorySyllabesVisuMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }

}