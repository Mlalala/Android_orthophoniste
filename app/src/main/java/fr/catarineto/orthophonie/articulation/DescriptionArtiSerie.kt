package fr.catarineto.orthophonie.articulation

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import fr.catarineto.orthophonie.R
import fr.catarineto.orthophonie.utils.DatabaseAccess
import android.os.Bundle
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.webkit.WebView
import android.widget.*
import kotlinx.android.synthetic.main.articulation_layout.*
import kotlinx.android.synthetic.main.articulation_layout.view.*
import java.io.File

class DescriptionArtiSerie : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articulation_layout)
        index_letter_level = intent.getIntArrayExtra("IntArray")
        var databaseAccess = DatabaseAccess.getInstance(this)
        databaseAccess.open()
        var articulation_level = databaseAccess.getArticulationMot(index_letter_level[1] + 1,index_letter_level[0] + 1)

        adapter_simple = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, articulation_level)
        /*
        var listview = findViewById(R.id.list_menu) as ListView
        listview.adapter = adapter_simple


        listview.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, "Position Clicked:"+" "+position, Toast.LENGTH_LONG).show()
            var intent = Intent(this, DescriptionArtiLevel::class.java)
            intent.putExtra("EXTRA_POSITION", position)
            startActivity(intent)
            finish()
        }*/

        FILE_RECORDING = "${externalCacheDir.absolutePath}/recorder.aac"

        initSerieData(databaseAccess)
        getView()
        setButtonRecordListener()
        setButtonPlayRecordingListener()
        enableDisableButtonPlayRecording()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults[0] == PERMISSION_GRANTED){
                record()
            }
        }
    }

    private fun enableDisableButtonPlayRecording(){
        buttonPlayRecording.isEnabled = doesFileExist()
    }

    private fun doesFileExist(): Boolean{
        val file = File(FILE_RECORDING)
        return file.exists()
    }

    private fun setButtonRecordListener(){
        buttonRecord.setOnClickListener {
            if(buttonRecord.text.toString().equals(getString(R.string.record), true)){
                record()
            }else{
                stopRecording()
                enableDisableButtonPlayRecording()
                buttonRecord.text = getString(R.string.record)
            }
        }
    }

    private fun setButtonPlayRecordingListener(){
        buttonPlayRecording.setOnClickListener {
            if(buttonPlayRecording.text.toString().equals(getString(R.string.playRecord), true)){
                buttonPlayRecording.text = getString(R.string.stopPlayingRecord)
                playRecording()
            }else{
                buttonPlayRecording.text = getString(R.string.playRecord)
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

    private fun record(){
        if(!isPermissionGranted()){
            requestAudioPermission()
            return
        }
        buttonRecord.text = getString(R.string.stopRecording)
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

    private fun playRecording(){
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setDataSource(FILE_RECORDING)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        mediaPlayer!!.setOnCompletionListener {
            buttonPlayRecording.text = getString(R.string.playRecord)
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
        val webView = findViewById(R.id.web_view) as WebView
        webView.loadUrl("file:///android_asset/AudioToWordPhono.gif")
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        val button = findViewById(R.id.test) as ImageButton
        button.setImageResource(R.drawable.ic_play_arti)

        return webView!!
    }

    private fun initSerieData(databaseAccess : DatabaseAccess){
        val sqlQuery = databaseAccess.getArticulationMot(indexSerie+1,indexInSerie+1)
        son=sqlQuery[0]
    }


}