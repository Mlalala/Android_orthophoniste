package fr.catarineto.orthophonie.visual.memorySyllabesVisu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.*
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess
import fr.catarineto.orthophonie.utils.Help

class MemorySyllabesVisuActivity : AppCompatActivity() {
    private var selected = ArrayList<Int>()
    private var buttonlist = ArrayList<Button>()
    private var nbCorrect = 0
    private  var serieSize = 0

    private val sizeText = 18f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        val indexSerie = intent.getIntExtra("EXTRA_POSITION",0)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val listElements = databaseAccess.getMemorySyllabesVisu(indexSerie+1)
        databaseAccess.close()
        serieSize = listElements.size
        listElements.addAll(listElements)
        listElements.shuffle()

        val rowParam = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f        )

        val buttonParam = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        )
        buttonParam.setMargins(10,6,10,6)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0 until listElements.size/2){
            val row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = rowParam

            for (j in 0..1){
                val button = Button(this)
                button.setAllCaps(false)
                button.textSize = 0f
                button.text = listElements[j+i*2]
                button.layoutParams = buttonParam
                button.setBackgroundColor(getColor(R.color.memoryDefault))
                row.addView(button)
                buttonlist.add(button)

                button.setOnClickListener {
                    selected.add(j+i*2)
                    clickProcess()
                }
            }
            table.addView(row)
        }
    }

    private fun clickProcess(){
        if (selected.size == 1){
            buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memorySelected))
            buttonlist[selected[0]].textSize = sizeText
        }
        else {
            buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memorySelected))
            buttonlist[selected[1]].textSize = sizeText

            if (selected[0] != selected[1] && buttonlist[selected[0]].text == buttonlist[selected[1]].text){
                buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonlist[selected[0]].setOnClickListener(null)
                buttonlist[selected[1]].setOnClickListener(null)
                selected.clear()

                nbCorrect += 1
                if (nbCorrect == serieSize){
                    manageItem()
                }
            }
            else {
                this@MemorySyllabesVisuActivity.buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryError))
                this@MemorySyllabesVisuActivity.buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryError))

                Thread(Runnable {
                    this@MemorySyllabesVisuActivity.runOnUiThread {
                        Thread.sleep(750)
                        this@MemorySyllabesVisuActivity.buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryDefault))
                        this@MemorySyllabesVisuActivity.buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryDefault))
                        buttonlist[selected[0]].textSize = 0f
                        buttonlist[selected[1]].textSize = 0f
                        this@MemorySyllabesVisuActivity.selected.clear()
                    }
                }).start()
            }
        }
    }


    private fun manageItem() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_layout, null)

        builder.setTitle("BRAVO !").setView(dialogView)

        builder.setPositiveButton("Revenir au menu") { _, _ ->
            val intent = Intent(this, MemorySyllabesVisuMenuActivity::class.java)
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
            R.id.action_help  -> Help(this@MemorySyllabesVisuActivity, getString(R.string.help_MemorySyllablesVisu), getString(R.string.title_MemorySyllablesVisu), "helptest")
    }
        return super.onOptionsItemSelected(item)
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