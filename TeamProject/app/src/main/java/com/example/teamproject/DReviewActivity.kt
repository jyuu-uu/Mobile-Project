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
        db = Firestore.create(applicationContext)

    }
}
