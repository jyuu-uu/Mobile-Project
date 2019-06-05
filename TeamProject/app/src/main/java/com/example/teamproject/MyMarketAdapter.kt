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

class MyMarketAdapter(var items:List<Place_info>, var fragment:Fragment, val clickListener: (Place_info) -> Unit) : RecyclerView.Adapter<MyMarketAdapter.ViewHolder>() {
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

        p0.name.text = items.get(p1).name.toString()
        p0.address.text = items.get(p1).formatted_address.toString()
        (p0 as ViewHolder).bind(items[p1], clickListener)
        Glide.with(fragment).load(items.get(p1).icon).into(p0.icon)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}