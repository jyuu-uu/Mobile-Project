package com.example.teamproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.myreportaftertravel.MyCafe
import com.example.myreportaftertravel.MyCafeAdapter
import kotlinx.android.synthetic.main.activity_dreview.*
import kotlinx.android.synthetic.main.activity_review.*

class DReviewActivity : AppCompatActivity() {

    lateinit var db :Firestore
    var data = ArrayList<MyCafe>()
    lateinit var adapter :MyCafeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dreview)
        init()
    }

    fun init(){
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        d_review.layoutManager = layoutManager
        adapter = MyCafeAdapter(data)
        d_review.adapter = adapter

        db = Firestore.create(applicationContext)
        val i = intent.getStringExtra("find")
        if(i != null){
            readDB(i)
        }
    }

    fun readDB(i:String) {
        val isExist = db!!.db?.collection("Travel")?.whereEqualTo("t_where",i)?.get()
            ?.addOnCompleteListener { task ->
                Log.e("리뷰", "접속")
                for (k in task.result!!) {
                    Log.e("리뷰", "$k")
                    data.add(MyCafe(k.get("t_where").toString(),k.get("t_when").toString(),k.get("t_who").toString()))
                }
                adapter.notifyDataSetChanged()
            }
    }
}
