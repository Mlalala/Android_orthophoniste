package fr.insa_cvl.orthophonie.phonology.memoryPhono

import android.app.PendingIntent.getActivity
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_phono_layout)

        val index_serie = intent.getIntExtra("EXTRA_POSITION",0)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var list_elements = databaseAccess.get_MemoryPhono(index_serie+1)
        list_elements.addAll(list_elements)
        list_elements.shuffle()

        Toast.makeText(this, list_elements.toString(),Toast.LENGTH_LONG).show()

        // TODO : match screen

        val row_param = TableRow.LayoutParams(
                10,
                10,
                1.0f
        )

        val button_param = TableRow.LayoutParams(
                1,
                1
        )

        val table = findViewById<TableLayout>(R.id.memoryphonotable)
        for (i in 0..list_elements.size/2-1){
            var row = TableRow(this)
            row.gravity = Gravity.CENTER
            row.layoutParams = row_param

            for (j in 0..1){
                var button = Button(this)
                button.text = list_elements[i+j*(list_elements.size/2)]
                //button.layoutParams = button_param
                row.addView(button)

                button.setOnClickListener(){
                    Toast.makeText(this, arrayListOf(i.toString(),j.toString()).toString(),Toast.LENGTH_LONG).show()
                    // TODO : play audio
                    button.setBackgroundColor(getColor(R.color.memoryPhonoSelected))
                }
            }
            table.addView(row)
        }
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