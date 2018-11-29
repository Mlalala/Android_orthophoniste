package fr.insa_cvl.orthophonie

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ImageView


class ModuleMenuListAdapter (context: Context, viewRes: Int, listMenu: ArrayList<ModuleMenuItem>): ArrayAdapter<ModuleMenuItem>(context,viewRes,listMenu) {

    private val res : Resources
    private var listMenu : ArrayList<ModuleMenuItem> ?= null //safe cast?
    private  var viewRes : Int = 0

    init{
        this.res = context.resources // ??
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
            val des = view!!.findViewById(R.id.module_des) as TextView
            val webView = view!!.findViewById(R.id.web_view) as WebView

            webView.loadUrl("file:///android_asset/"+itemList.pic+".gif")
            webView.getSettings().setLoadWithOverviewMode(true)
            webView.getSettings().setUseWideViewPort(true)


            title.text = itemList.titre
            des.text = itemList.des

            val img = view!!.findViewById(R.id.clicimg) as ImageView
            img.setOnClickListener {
                var intent = Intent(context, itemList.act)
                (context as Activity).startActivity(intent)
                (context as Activity).finish()
            }
        }
        return view!!
    }
}