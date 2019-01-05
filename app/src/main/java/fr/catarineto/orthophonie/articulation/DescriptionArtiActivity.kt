package fr.catarineto.orthophonie.articulation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess
import android.os.Bundle
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.webkit.WebView
import android.widget.*
import fr.catarineto.orthophonie.utils.Help
import kotlinx.android.synthetic.main.articulation_layout.*
import java.io.File

class DescriptionArtiActivity : AppCompatActivity() {
    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null

    var FILE_RECORDING = ""

    val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
    val AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO
    val PERMISSION_REQUEST_CODE = 100

    private var index_letter_level: IntArray = intArrayOf(0)

    private var adapter_simple : ArrayAdapter<String>? = null
    private var son : String = ""
    private var indexInSerie : Int = 0
    private var indexSerie : Int = 0
    private var state_button : Int = 1
    private var state_button_play_record = 1
    private var index_letter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)
        index_letter = intent.getIntExtra("EXTRA_POSITION",0)
        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        //var articulation_level = databaseAccess.getArticulationMot(index_letter_level[1] + 1)
        //var articulation_level = databaseAccess.getArticulationSon(index_letter_level[1],index_letter_level[0])

        //adapter_simple = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, articulation_level)

        //var son = findViewById(R.id.sonArticulation) as TextView
        //son.text = "test"

        /*
        listview.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, "Position Clicked:"+" "+position, Toast.LENGTH_LONG).show()
            var intent = Intent(this, DescriptionArtiSon::class.java)
            intent.putExtra("EXTRA_POSITION", position)
            startActivity(intent)
            finish()
        }*/

        FILE_RECORDING = "${externalCacheDir.absolutePath}/recorder.aac"

        //val buttonRecord = findViewById(R.id.buttonRecord) as Button
        //val buttonPlayRecording =findViewById(R.id.buttonPlayRecording) as Button
        val imagebuttonrecord = findViewById(R.id.buttonRecord) as ImageButton
        imagebuttonrecord.setImageResource(R.drawable.ic_play_arti)

        val buttonPlayRecording =findViewById(R.id.buttonPlayRecording) as ImageButton
        buttonPlayRecording.setImageResource(R.drawable.ic_play_record_arti)

        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(this, resources.getIdentifier("testaudio","raw","fr.catarineto.orthophonie"))
        mediaPlayer!!.start()


        initSerieData(databaseAccess)
        getView()
        setButtonRecordListener(buttonRecord,buttonPlayRecording )
        setButtonPlayRecordingListener(buttonPlayRecording)
        enableDisableButtonPlayRecording(buttonPlayRecording)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val buttonRecord = findViewById(R.id.buttonRecord) as ImageButton
        buttonRecord.setImageResource(R.drawable.ic_play_arti)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PERMISSION_GRANTED){
                record(buttonRecord)
            }
        }
    }

    private fun enableDisableButtonPlayRecording(buttonPlayRecordingt: ImageButton){
        buttonPlayRecordingt.isEnabled = doesFileExist()
    }

    private fun doesFileExist(): Boolean{
        val file = File(FILE_RECORDING)
        return file.exists()
    }

    private fun setButtonRecordListener(buttonRecordt : ImageButton, buttonPlayRecordingt: ImageButton){

        buttonRecordt.setOnClickListener {
            if(state_button == 1){
                record(buttonRecordt)
            }else{
                stopRecording()
                enableDisableButtonPlayRecording(buttonPlayRecordingt)
                buttonRecordt.setImageResource(R.drawable.ic_play_arti)
                state_button = 1
            }
        }

        /*
        buttonRecordt.setOnClickListener{
            record(buttonRecordt)
        }*/
    }

    private fun setButtonPlayRecordingListener(buttonPlayRecordingt : ImageButton){
        buttonPlayRecordingt.setOnClickListener {
            if(state_button_play_record ==1){
                buttonPlayRecordingt.setImageResource(R.drawable.ic_stop_play_record)
                playRecording(buttonPlayRecordingt)
            }else{
                state_button_play_record = 1
                buttonPlayRecordingt.setImageResource(R.drawable.ic_play_record_arti)
                stopPlayingRecording()
            }
        }
    }

    private fun isPermissionGranted(): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) checkSelfPermission(AUDIO_PERMISSION) == PERMISSION_GRANTED
        else return true

    }

    private fun requestAudioPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(arrayOf(AUDIO_PERMISSION), PERMISSION_REQUEST_CODE)
        }
    }

    private fun record(buttonRecordt : ImageButton){
        if(!isPermissionGranted()){
            requestAudioPermission()
            return
        }

        state_button = 0
        buttonRecordt.setImageResource(R.drawable.ic_record_arti)
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder!!.setOutputFile(FILE_RECORDING)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder!!.prepare()
        mediaRecorder!!.start()
    }

    private fun stopRecording(){
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    private fun playRecording(buttonPlayRecordingt : ImageButton){
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setDataSource(FILE_RECORDING)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        mediaPlayer!!.setOnCompletionListener {
            state_button_play_record = 1
            buttonPlayRecordingt.setImageResource(R.drawable.ic_play_record_arti)
        }
    }

    private fun stopPlayingRecording(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun getView(): WebView {
        val textview = findViewById<TextView>(R.id.sonArticulation)
        textview.text = "" + son

        val text_record = findViewById<TextView>(R.id.text_record)
        text_record.text = "   record"

        val text_play_record =  findViewById<TextView>(R.id.text_play_record)
        text_play_record.text = "   play record"

        val audioView = findViewById<ImageView>(R.id.imagePlay)

        audioView.setOnClickListener {
            mediaPlayer?.reset()
            mediaPlayer = MediaPlayer.create(this, resources.getIdentifier("testaudio", "raw", "fr.catarineto.orthophonie"))
            mediaPlayer!!.start()
        }

        val webView = findViewById(R.id.web_view) as WebView
        webView.loadUrl("file:///android_asset/sample.gif")
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        return webView!!
    }

    private fun initSerieData(databaseAccess : DatabaseAccess){
        val sqlQuery = databaseAccess.getArticulationLetter()
        son=sqlQuery[index_letter]
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, DescriptionArtiMenu::class.java)
            startActivity(intent)
            finish()
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.help,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.action_help  -> Help(this@DescriptionArtiActivity, getString(R.string.help_Articulation), getString(R.string.title_arti), "helptest")
        }
        return super.onOptionsItemSelected(item)
    }

}