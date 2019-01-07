package fr.catarineto.orthophonie.phonology.memoryPhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess
import fr.catarineto.orthophonie.utils.Help

class MemoryPhonoActivity : AppCompatActivity() {

    private var selected = ArrayList<Int>()
    private var buttonList = ArrayList<Button>()
    private var nbCorrect = 0
    private  var serieSize = 0

    private var media : MediaPlayer? = null

    private val sizeText = 18f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        val indexSerie = intent.getIntExtra("EXTRA_POSITION",0)

        val databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        val listElements = databaseAccess.getMemoryPhono(indexSerie+1)
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
        buttonParam.setMargins(10,10,10,10)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0 until listElements.size/2){
            val row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = rowParam

            for (j in 0..1){
                val button = Button(this)
                button.textSize = 0f
                button.isAllCaps = false
                button.text = listElements[j+i*2]
                button.layoutParams = buttonParam
                button.setBackgroundColor(getColor(R.color.memoryDefault))
                row.addView(button)
                buttonList.add(button)

                button.setOnClickListener {
                    media?.reset()
                    media = MediaPlayer.create(this, resources.getIdentifier("memoryphono" + button.text.toString().toLowerCase(), "raw", "fr.catarineto.orthophonie"))
                    media!!.start()
                    selected.add(j+i*2)
                    clickProcess()
                }
            }
            table.addView(row)
        }
    }

    private fun clickProcess(){
        if (selected.size == 1){
            buttonList[selected[0]].setBackgroundColor(getColor(R.color.memorySelected))
        }
        else {
            buttonList[selected[1]].setBackgroundColor(getColor(R.color.memorySelected))
            if (selected[0] != selected[1] && buttonList[selected[0]].text == buttonList[selected[1]].text){
                buttonList[selected[0]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonList[selected[1]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonList[selected[0]].setOnClickListener(null)
                buttonList[selected[1]].setOnClickListener(null)
                buttonList[selected[0]].textSize = sizeText
                buttonList[selected[1]].textSize = sizeText
                selected.clear()

                nbCorrect += 1
                if (nbCorrect == serieSize){
                    manageItem()
                }
            }
            else {
                this@MemoryPhonoActivity.buttonList[selected[0]].setBackgroundColor(getColor(R.color.memoryError))
                this@MemoryPhonoActivity.buttonList[selected[1]].setBackgroundColor(getColor(R.color.memoryError))

                Thread(Runnable {
                    this@MemoryPhonoActivity.runOnUiThread {
                        Thread.sleep(750)
                        this@MemoryPhonoActivity.buttonList[selected[0]].setBackgroundColor(getColor(R.color.memoryDefault))
                        this@MemoryPhonoActivity.buttonList[selected[1]].setBackgroundColor(getColor(R.color.memoryDefault))
                        this@MemoryPhonoActivity.selected.clear()
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
            val intent = Intent(this, MemoryPhonoMenuActivity::class.java)
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
            R.id.action_help  -> Help(this@MemoryPhonoActivity, getString(R.string.help_MemoryPhono), getString(R.string.title_MemoryPhono), "helptest")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MemoryPhonoMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
}