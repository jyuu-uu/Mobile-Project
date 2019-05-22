package com.example.myreportaftertravel

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.teamproject.R

class MyCafeAdapter(var items:ArrayList<MyCafe>) : RecyclerView.Adapter<MyCafeAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var country : TextView
        var point:TextView
        var icon : ImageView
        init{
            country = itemView.findViewById(R.id.c_country)
            point = itemView.findViewById(R.id.c_point)
            icon = itemView.findViewById(R.id.listicon)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.review_card_layout, p0, false)
        return ViewHolder(v)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return items.size
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {    //data, View connection
        p0.country.text = items.get(p1).country.toString()
        p0.point.text = items.get(p1).point.toString()
        Log.v("wls", items.get(p1).weather)
        when(items.get(p1).weather){
            "더움"-> p0.icon.setImageResource(R.drawable.sun)
            "흐림"-> p0.icon.setImageResource(R.drawable.cloud_review)
            "비옴"-> p0.icon.setImageResource(R.drawable.rainy)
            else->p0.icon.setImageResource(R.drawable.sun)
        }
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun moveItem(pos1:Int, pos2:Int){
        val item1 = items.get(pos1)
        items.removeAt(pos1)
        items.add(pos2, item1)
        notifyItemMoved(pos1, pos2)
    }
}