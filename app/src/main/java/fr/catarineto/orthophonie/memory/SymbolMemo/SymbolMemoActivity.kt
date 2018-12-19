package fr.catarineto.orthophonie.memory.SymbolMemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
<<<<<<< HEAD:app/src/main/java/fr/catarineto/orthophonie/memory/SymbolMemo/SymbolMemoActivity.kt
import fr.catarineto.orthophonie.R
=======
import android.view.MotionEvent
import android.view.View
import fr.catarinetostudio.orthophonie.R
import fr.catarinetostudio.orthophonie.memory.MemoryMenuActivity
>>>>>>> master:app/src/main/java/fr/catarinetostudio/orthophonie/memory/SymbolMemo/SymbolMemoActivity.kt

class SymbolMemoActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_memo_layout)

        val actionBar = supportActionBar

        actionBar!!.hide()

        //setContentView(R.layout.activity_shapes_view)

        var listener = View.OnTouchListener(function = { view : View, motionEvent : MotionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height/2
                view.x = motionEvent.rawX - view.width/2
            }

            true

        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, SymbolMemoMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }
}