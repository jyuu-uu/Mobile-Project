package com.example.teamproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list2.*
import kotlinx.android.synthetic.main.fragment_main_second.*
import kotlinx.android.synthetic.main.fragment_main_second.view.*

class main_second : Fragment() {

    lateinit var v: View
    lateinit var itemAdapter1: ItemAdapter
    lateinit var item1: ArrayList<Item>
    lateinit var db: Firestore
    var t_id = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("순서", "메인2")

        v = inflater.inflate(R.layout.fragment_main_second, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        db = Firestore.create(activity!!.applicationContext)

        Log.e("db널이나 너?","a아 시발")

        initOne()
        loadDB()
    }

       fun initOne() {
//        area1 = activity!!.findViewById(R.id.pane1)
//        area1.setOnDragListener(DragListener())
        item1 = ArrayList()
        val listView1 = v!!.findViewById<RecyclerView>(R.id.ws_list)
        val layoutManager1 = LinearLayoutManager(context)

        listView1.layoutManager = layoutManager1
        itemAdapter1 = ItemAdapter(item1)
        listView1.adapter = itemAdapter1
    }

    fun loadDB() {
        db!!.db?.collection("Travel")!!.whereEqualTo("u_id", MainActivity.User.toString())
            .whereEqualTo("t_fin", false)
          //  .orderBy("t_when")
            .get()
            .addOnSuccessListener {
                val c = it.documents

                if (c.isNotEmpty()) {
                    val q = c[0]
                    t_id = q.get("t_id").toString().toInt()
                    v.findViewById<TextView>(R.id.ws_title).text = q.get("t_where").toString()
                    v.findViewById<TextView>(R.id.ws_date).text = q.get("t_when").toString()
                    Log.e("번호야","$t_id")
                }
                    LoadTravel()
            }
    }

    fun LoadTravel(){
        if(t_id == -1){
            v.ws_title.text = "일정 없음"
            v.ws_date.text = "----------"
        }
        else{
            Log.e("일정 있어","ㅇㅅㅇ")
            db!!.db!!.collection("Item").whereEqualTo("i_tnum",t_id).get()
                .addOnSuccessListener {
                    val v = it.documents
                    for(k in v)
                    {
                        item1.add(Item(1, k.get("i_name").toString(), k.get("i_tnum").toString().toInt()
                            , k.get("i_ugender").toString().toBoolean(), k.get("i_uage").toString().toInt()))
                    }
                    itemAdapter1.notifyDataSetChanged()
                }
        }
    }
}
