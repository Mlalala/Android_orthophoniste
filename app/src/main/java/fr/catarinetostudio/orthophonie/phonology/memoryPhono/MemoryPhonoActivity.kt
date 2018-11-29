package fr.catarinetostudio.orthophonie.phonology.memoryPhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
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
import fr.catarinetostudio.orthophonie.db_utils.DatabaseAccess


class MemoryPhonoActivity : AppCompatActivity() {

    private var selected = ArrayList<Int>()
    private var buttonlist = ArrayList<Button>()
    private var nb_correct = 0
    private  var serie_size = 0

    private var media : MediaPlayer? = null

    private val size_text = 18f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        val index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var list_elements = databaseAccess.get_MemoryPhono(index_serie+1)
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
        button_param.setMargins(10,10,10,10)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0..list_elements.size/2-1){
            var row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = row_param

            for (j in 0..1){
                var button = Button(this)
                button.textSize = 0f
                button.setAllCaps(false)
                button.text = list_elements[j+i*2]
                button.layoutParams = button_param
                button.setBackgroundColor(getColor(R.color.memoryDefault))
                row.addView(button)
                buttonlist.add(button)

                button.setOnClickListener(){
                    media?.reset()
                    media = MediaPlayer.create(this, getResources().getIdentifier("memoryphono" + button.text.toString().toLowerCase(), "raw", "fr.insa_cvl.orthophonie"))
                    media!!.start()
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
        }
        else {
            buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memorySelected))
            if (selected[0] != selected[1] && buttonlist[selected[0]].text == buttonlist[selected[1]].text){
                buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryValid))
                buttonlist[selected[0]].setOnClickListener(null)
                buttonlist[selected[1]].setOnClickListener(null)
                buttonlist[selected[0]].textSize = size_text
                buttonlist[selected[1]].textSize = size_text
                selected.clear()

                nb_correct += 1
                if (nb_correct == serie_size){
                    manageItem()
                }
            }
            else {
                this@MemoryPhonoActivity.buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryError))
                this@MemoryPhonoActivity.buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryError))

                Thread(Runnable {
                    this@MemoryPhonoActivity.runOnUiThread(java.lang.Runnable {
                        Thread.sleep(750)
                        this@MemoryPhonoActivity.buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryDefault))
                        this@MemoryPhonoActivity.buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryDefault))
                        this@MemoryPhonoActivity.selected.clear()
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

        builder.setPositiveButton("Revenir au menu") { dialog, id ->
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
            R.id.action_help  -> manageMenu(getString(R.string.help),getString(R.string.help_MemoryPhono))
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
            intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour l'activitée " + getString(R.string.title_MemoryPhono) +" de l'Application Android Orthophonie")
            startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
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