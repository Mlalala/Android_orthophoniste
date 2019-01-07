package fr.catarineto.orthophonie.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.content.Intent
import android.widget.ImageView
import fr.catarineto.orthophonie.R

class ModuleMenuListAdapter (context : Activity, viewRes: Int, listMenu: ArrayList<ModuleMenuItem>): ArrayAdapter<ModuleMenuItem>(context,viewRes,listMenu) {

    private var listMenu : ArrayList<ModuleMenuItem> ?= null
    private  var viewRes : Int = 0

    init{
        this.listMenu = listMenu
        this.viewRes = viewRes
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if( view == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(viewRes, parent, false)
        }
        val itemList = listMenu?.get(position)

        if (itemList != null) {
            val title = view!!.findViewById(R.id.module_title) as TextView
            //val des = view.findViewById(R.id.module_des) as TextView
            val webView = view.findViewById(R.id.web_view) as WebView
            val helpImg = view.findViewById<ImageView>(R.id.help_click)

            webView.loadUrl("file:///android_asset/"+itemList.pic+".gif")
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true

            title.text = itemList.titre
            //des.text = itemList.des

            val img = view.findViewById(R.id.clicimg) as ImageView
            img.setOnClickListener {
                val intent = Intent(context, itemList.act)
                (context as Activity).startActivity(intent)
                (context as Activity).finish()
            }

            helpImg.setOnClickListener {
                Help(context as Activity,itemList.des,itemList.titleAc,itemList.audio)
            }
        }
        return view!!
    }
}