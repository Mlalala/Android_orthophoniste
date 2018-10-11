package fr.insa_cvl.orthophonie

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MenuListAdapter(context: Context, viewRes: Int, listMenu: ArrayList<MenuItem>): ArrayAdapter<MenuItem>(context,viewRes,listMenu) {

    private val res : Resources
    private var listMenu : ArrayList<MenuItem> ?= null //safe cast?
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
            val title = view!!.findViewById(R.id.title) as TextView
            val logo = view.findViewById(R.id.menu_logo) as ImageView

            title.setText(itemList.titre)
            logo.setImageResource(itemList.logo)

        }
        return view!!

    }
}