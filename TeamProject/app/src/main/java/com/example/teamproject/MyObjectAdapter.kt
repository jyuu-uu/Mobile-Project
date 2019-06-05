package com.example.myreportaftertravel

import android.location.Geocoder
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.teamproject.Place_info
import com.example.teamproject.R

class MyObjectAdapter(var items:List<String>) : RecyclerView.Adapter<MyObjectAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name : TextView
        var address:TextView
        var icon : ImageView
        init{
            name = itemView.findViewById(R.id.m_name)
            address = itemView.findViewById(R.id.m_address)
            icon = itemView.findViewById(R.id.market_listicon)
        }
        fun bind(part: Place_info, clickListener: (Place_info) -> Unit) {
            itemView.setOnClickListener { clickListener(part)}
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.market_card_layout, p0, false)
        return ViewHolder(v)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return items.size
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {    //data, View connection

        p0.name.text = items.get(p1)
        p0.icon.setImageResource(R.drawable.ic_flight_land_black_24dp)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}