package fr.catarinetostudio.orthophonie.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import fr.catarinetostudio.orthophonie.R

class Help(ac : Activity, text : String, titleAc : String, audio : String) {
    init{
        val builder = AlertDialog.Builder(ac)

        val inflater = ac.layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_text_picture_layout, null)

        val img= dialogView.findViewById<ImageView>(R.id.imgPlayHelp)
        img.setImageResource(R.drawable.ic_volume_up)

        img.setOnClickListener {
            MediaPlayer.create(ac, ac.resources.getIdentifier(audio,"raw","fr.catarinetostudio.orthophonie")).start()
        }

        dialogView.findViewById<TextView>(R.id.textPlayHelp).text = ac.getString(R.string.help_play)

        builder.setTitle(ac.getString(R.string.help)).setView(dialogView)
        builder.setMessage(text)
        builder.setPositiveButton(ac.getString(R.string.suggestion)) { _, _ ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + ac.getString(R.string.email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion pour l'activitée " + titleAc +" de l'Application Android Orthophonie")
            ac.startActivity(intent)
        }

        val dialog = builder.create()
        dialog.show()
    }
}