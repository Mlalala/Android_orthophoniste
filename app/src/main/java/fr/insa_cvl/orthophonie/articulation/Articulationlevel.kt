package fr.insa_cvl.orthophonie.articulation

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.insa_cvl.orthophonie.R
import fr.insa_cvl.orthophonie.db_utils.DatabaseAccess


class Articulationlevel: AppCompatActivity() {

    private var index_in_serie : Int = 0
    private var index_serie : Int = 0
    private var proposal : List<String>? = null
    private var answer : Int = 0

    private var length_serie : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)

        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()

        var sql_query = databaseAccess.count_AudioToWordPhono(index_serie+1)
        length_serie = sql_query

        //init proposals, answer and audio
        
    }

    fun init_serie_data(databaseAccess : DatabaseAccess){
        //databaseAccess.open()
        var sql_query = databaseAccess.get_AudioToWordPhono(index_serie+1,index_in_serie+1)
        //databaseAccess.close()

        proposal = sql_query[0].toString().split("-")
        answer = sql_query[1].toInt()
    }

}