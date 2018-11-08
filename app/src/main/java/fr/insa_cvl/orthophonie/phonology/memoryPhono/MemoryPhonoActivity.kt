package fr.insa_cvl.orthophonie.phonology.memoryPhono

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess


class MemoryPhonoActivity : AppCompatActivity() {

    private var selected = ArrayList<Int>()
    private var buttonlist = ArrayList<Button>()
    private var nb_correct = 0
    private  var serie_size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_phono_layout)

        val index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var list_elements = databaseAccess.get_MemoryPhono(index_serie+1)
        serie_size = list_elements.size
        list_elements.addAll(list_elements)
        list_elements.shuffle()

        Toast.makeText(this, list_elements.toString(),Toast.LENGTH_LONG).show()

        // TODO : match screen

        val row_param = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
        )

        val button_param = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT
        )
        button_param.setMargins(5,5,5,5)

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0..list_elements.size/2-1){
            var row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = row_param
            table.addView(row)

            for (j in 0..1){
                var button = Button(this)
                button.text = list_elements[j+i*2]
                button.layoutParams = button_param
                button.setBackgroundColor(getColor(R.color.memoryPhonoDefault))
                row.addView(button)
                buttonlist.add(button)

                button.setOnClickListener(){
                    Toast.makeText(this, arrayListOf(i.toString(),j.toString()).toString(),Toast.LENGTH_LONG).show()
                    // TODO : play audio
                    selected.add(j+i*2)
                    click_process()
                }
            }
        }
    }

    fun click_process(){
        if (selected.size == 1){
            buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryPhonoSelected))
        }
        else {
            buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryPhonoSelected))
            //Thread.sleep(500)
            if (buttonlist[selected[0]].text == buttonlist[selected[1]].text){
                Toast.makeText(this, "correct",Toast.LENGTH_LONG).show()
                buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryPhonoValid))
                buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryPhonoValid))
                buttonlist[selected[0]].setOnClickListener(null)
                buttonlist[selected[1]].setOnClickListener(null)
                selected.clear()

                nb_correct += 1
                if (nb_correct == serie_size){
                    manageItem()
                }
            }
            else {
                Toast.makeText(this, "incorrect",Toast.LENGTH_LONG).show()
                //TODO : TIMER RED COLOUR
                //buttonlist[selected[0]].setBackgroundColor(getColor(R.color.alertcolour))
                //buttonlist[selected[1]].setBackgroundColor(getColor(R.color.alertcolour))
                buttonlist[selected[0]].setBackgroundColor(getColor(R.color.memoryPhonoDefault))
                buttonlist[selected[1]].setBackgroundColor(getColor(R.color.memoryPhonoDefault))
                selected.clear()
            }
        }
    }

    fun manageItem() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_phonology_layout, null)

        builder.setTitle("BRAVO !").setView(dialogView)

        // Add the button
        builder.setPositiveButton("Revenir au menu") { dialog, id ->
            val intent = Intent(this, MemoryPhonoMenuActivity::class.java)
            startActivity(intent)
        }

        // Create the AlertDialog
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