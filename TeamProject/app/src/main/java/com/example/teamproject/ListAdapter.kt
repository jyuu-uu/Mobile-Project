package com.example.teamproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyAdapter (context: Context, val resource:Int, var list:ArrayList<MyTravel>)
    : ArrayAdapter<MyTravel>(context,resource,list)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v:View?=convertView
        if(v==null){
            var vi=context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v=vi.inflate(R.layout.list_row, null)

        }
        val p=list.get(position)
        v!!.findViewById<TextView>(R.id.textView).text=p.where
        v!!.findViewById<TextView>(R.id.textView9).text=p.period

        return v
        //return super.getView(position, convertView, parent)
    }
//    fun update(list:ArrayList<MyTravel>){
//        travels=list
//        notifyDataSetChanged()
//    }
}