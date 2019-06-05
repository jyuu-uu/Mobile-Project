package com.example.teamproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list2.*
import kotlinx.android.synthetic.main.fragment_main_second.*
import kotlinx.android.synthetic.main.fragment_main_second.view.*

class main_second : Fragment() {

    lateinit var v:View
    lateinit var itemAdapter1: ItemAdapter
    lateinit var item1:ArrayList<Item>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("순서","메인2")

        v= inflater.inflate(R.layout.fragment_main_second, container, false)
        initOne()
        initBtn()
        return v
    }

    fun initBtn(){
        v.ws_btn.setOnClickListener {

        }


    }
    fun initOne(){
//        area1 = activity!!.findViewById(R.id.pane1)
//        area1.setOnDragListener(DragListener())
        item1 = ArrayList()
        item1.add(Item(0,"a",1,true,5))
        val listView1 = v!!.findViewById<RecyclerView>(R.id.ws_list)
        val layoutManager1 = LinearLayoutManager(context)

        listView1.layoutManager = layoutManager1
        itemAdapter1 = ItemAdapter(item1)
        listView1.adapter = itemAdapter1
    }

}
