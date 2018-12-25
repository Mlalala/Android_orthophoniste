package fr.catarineto.orthophonie.memory.SymbolMemo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.IntegerRes
import android.support.v7.app.AppCompatActivity
import android.view.*
import fr.catarineto.orthophonie.R
import android.widget.*
import fr.catarineto.orthophonie.memory.MemoryMenuActivity
import kotlinx.android.synthetic.main.symbol_memo_layout.*
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer
import kotlin.random.Random
import fr.catarineto.orthophonie.utils.Help

class SymbolMemoActivity : AppCompatActivity()  {
    private var number_random_first_image : Int = 0
    private var number_random_second_image : Int = 0
    private var number_random_third_image : Int = 0
    private var number_random_fourth_image : Int = 0
    private var number_random_fifth_image : Int = 0

    private var first_increment : Int = 0
    private var second_increment : Int = 0
    private var third_increment : Int = 0
    private var fourth_increment : Int = 0
    private var fifth_increment : Int = 0

    private var list= arrayOf(R.drawable.check,R.drawable.close,R.drawable.cloud,R.drawable.round,R.drawable.star, R.drawable.triangle);
    private var list_selection = arrayListOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.symbol_memo_layout)

        val actionBar = supportActionBar


        actionBar!!.hide()
        number_random_first_image = Random.nextInt(0,list.size)
        val image1 = findViewById(R.id.symbol1) as ImageView
        choiceSymbol(image1, number_random_first_image)

        number_random_second_image = Random.nextInt(0, list.size)
        val image2 = findViewById(R.id.symbol2) as ImageView
        choiceSymbol(image2, number_random_second_image)

        number_random_third_image = Random.nextInt(0, list.size)
        val image3 = findViewById(R.id.symbol3) as ImageView
        choiceSymbol(image3, number_random_third_image)

        number_random_fourth_image = Random.nextInt(0, list.size)
        val image4 = findViewById(R.id.symbol4) as ImageView
        choiceSymbol(image4, number_random_fourth_image)

        number_random_fifth_image = Random.nextInt(0,list.size)
        val image5 = findViewById(R.id.symbol5) as ImageView
        choiceSymbol(image5, number_random_fifth_image)

        //Toast.makeText(this,"list = " + list.size.toString(), Toast.LENGTH_SHORT).show()

        list_selection.add(list[number_random_first_image])
        list_selection.add(list[number_random_second_image])
        list_selection.add(list[number_random_third_image])
        list_selection.add(list[number_random_fourth_image])
        list_selection.add(list[number_random_fifth_image])




        val imageMemo1 = findViewById(R.id.symbolMemo1) as ImageButton
        imageMemo1.setImageResource(list[0])

        val imageMemo2 = findViewById(R.id.symbolMemo2) as ImageButton
        imageMemo2.setImageResource(list[0])

        val imageMemo3 = findViewById(R.id.symbolMemo3) as ImageButton
        imageMemo3.setImageResource(list[0])

        val imageMemo4 = findViewById(R.id.symbolMemo4) as ImageButton
        imageMemo4.setImageResource(list[0])

        val imageMemo5 = findViewById(R.id.symbolMemo5) as ImageButton
        imageMemo5.setImageResource(list[0])

        imageMemo1.setOnClickListener {
            hideSymbol(image1)
            hideSymbol(image2)
            hideSymbol(image3)
            hideSymbol(image4)
            hideSymbol(image5)
            if(first_increment == list.size -1){
            first_increment = 0
            }
            else{
            first_increment = first_increment + 1
            }
            changeSymbol(first_increment, imageMemo1)
        }

        imageMemo2.setOnClickListener {
            hideSymbol(image1)
            hideSymbol(image2)
            hideSymbol(image3)
            hideSymbol(image4)
            hideSymbol(image5)
            if(second_increment == list.size -1){
                second_increment = 0
            }
            else{
                second_increment = second_increment + 1
            }
            changeSymbol(second_increment, imageMemo2)
        }

        imageMemo3.setOnClickListener {
            hideSymbol(image1)
            hideSymbol(image2)
            hideSymbol(image3)
            hideSymbol(image4)
            hideSymbol(image5)
            if(third_increment == list.size -1){
                third_increment = 0
            }
            else{
                third_increment = third_increment + 1
            }
            changeSymbol(third_increment, imageMemo3)
        }

        imageMemo4.setOnClickListener {
            hideSymbol(image1)
            hideSymbol(image2)
            hideSymbol(image3)
            hideSymbol(image4)
            hideSymbol(image5)
            if(fourth_increment == list.size -1){
                fourth_increment = 0
            }
            else{
                fourth_increment = fourth_increment + 1
            }
            changeSymbol(fourth_increment, imageMemo4)
        }

        imageMemo5.setOnClickListener {
            hideSymbol(image1)
            hideSymbol(image2)
            hideSymbol(image3)
            hideSymbol(image4)
            hideSymbol(image5)
            if(fifth_increment == list.size -1){
                fifth_increment = 0
            }
            else{
                fifth_increment = fifth_increment + 1
            }
            changeSymbol(fifth_increment, imageMemo5)
        }

        val buttoncheckanswer = findViewById<Button>(R.id.buttoncheckanswer)
        buttoncheckanswer.text = "check"
        buttoncheckanswer.setOnClickListener { isCorrect() }

        val consigne = findViewById(R.id.consigne) as TextView
        consigne.text = "Retenez les symboles et restituez les"





        //hideSymbol(image1)
        //hideSymbol(image2)
        //hideSymbol(image3)
        //hideSymbol(image4)
        //hideSymbol(image5)

        //Toast.makeText(this,"nombre random = " + number_random.toString(), Toast.LENGTH_SHORT).show()

        //setContentView(R.layout.activity_shapes_view)





    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MemoryMenuActivity::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }

    private fun choiceSymbol(image : ImageView, nb_random: Int){

        image.setImageResource(list[nb_random])

    }

    private fun hideSymbol(image : ImageView){
        image.setImageResource(R.drawable.symbolhide)
    }

    private fun changeSymbol(increment : Int, imageButton : ImageButton) {
        for (i in 0..list.size){
            if (increment == i){
                imageButton.setImageResource(list[i])
            }
        }

    }

    private fun isCorrect(){

        //Toast.makeText(this,"nombre random = ", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        var intent = Intent(this, SymbolMemoActivity::class.java)

        val dialogView = inflater.inflate(R.layout.alert_layout, null)
        //builder.setTitle("CORRECT").setView(dialogView)

        if((number_random_first_image == first_increment) && (number_random_second_image == second_increment) && (number_random_third_image == third_increment) && (number_random_fourth_image == fourth_increment) && (number_random_fifth_image == fifth_increment)){
            builder.setTitle("CORRECT").setView(dialogView)
            builder.setPositiveButton("Nouvelle série") { _, _ -> startActivity(intent)
                finish()}
        }
        else{
            builder.setTitle("FAUX").setView(dialogView)
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
            R.id.action_help  -> Help(this@SymbolMemoActivity, getString(R.string.help_SymbolMemo), getString(R.string.des_SymbolsMemo), "helptest")
        }
        return super.onOptionsItemSelected(item)
    }

}