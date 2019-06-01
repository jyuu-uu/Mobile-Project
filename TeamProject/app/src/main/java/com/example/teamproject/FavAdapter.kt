package com.example.teamproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.auth.User

class FavAdapter (var items:MutableList<FavData>,var c: Context, var _user:String) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title : TextView
        var country: TextView
        var writer : TextView
        init{
            title = itemView.findViewById(R.id.fav_title)
            country = itemView.findViewById(R.id.fav_country)
            writer = itemView.findViewById(R.id.fav_writer)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.fav_form, p0, false)
        return ViewHolder(v)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return items.size
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {    //data, View connection
        p0.title.text = items.get(p1)._title.toString()
        p0.country.text = items.get(p1)._country.toString()
        p0.writer.text = items.get(p1)._writer.toString()
        Log.v("fav", "$items")
    }

    fun removeItem(pos:Int){ // 밀어서 없애면
        // 데이터베이스에서도 삭제되어야함
        var db = Firestore.create(c)
        db.deleteData(_user,1)
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }


}