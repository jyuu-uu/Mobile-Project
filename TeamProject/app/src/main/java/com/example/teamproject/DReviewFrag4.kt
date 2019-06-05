package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.fragment_dreview_frag4.view.*

class DReviewFrag4 : Fragment() {

    lateinit var v:View
    lateinit var itemAdapter1: ItemAdapter
    lateinit var item1: ArrayList<Item>
    lateinit var db: Firestore
    var t_id = -1
    var r_star = ""
    var r_text=""
    var r_textname =""

    companion object{
        fun start(t_id:Int):DReviewFrag4{
            val f = DReviewFrag4()
            f.t_id = t_id
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_dreview_frag4, container, false)


        db = Firestore.create(activity!!.applicationContext)
        initOne()
        readData()
    return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    fun initOne() {
        item1 = ArrayList()
        val listView1 = v.findViewById<RecyclerView>(R.id.dr_list)
        val layoutManager1 = LinearLayoutManager(context)

        listView1.layoutManager = layoutManager1
        itemAdapter1 = ItemAdapter(item1)
        listView1.adapter = itemAdapter1


    }

    fun readData(){
        db!!.db!!.collection("Review").whereEqualTo("t_id",t_id).get()
            .addOnSuccessListener {
              val a=  it.documents
                for( b in a){
                    r_star = b.get("r_star").toString()
                    r_text =b.get("r_text").toString()
                    r_textname = b.get("r_text_name").toString()

                    val c = b.get("r_object")
                    if (c != null){
                        val d = c as ArrayList<String>
                        for(e in d){
                            item1.add(Item(-1,e.toString(),-1,false,-1))
                        }
                    }
                    itemAdapter1.notifyDataSetChanged()
                    setText()
                }
            }
    }

    fun setText(){
        v.dr_score.setText(r_star)
        v.dr_text.setText(r_text)
        v.dr_textname.setText(r_textname)
        Log.e("끝!","sdf")
    }

}
