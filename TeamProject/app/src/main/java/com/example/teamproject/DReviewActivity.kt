package com.example.teamproject

import android.os.Build.VERSION_CODES.P
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.myreportaftertravel.MyCafe
import com.example.myreportaftertravel.MyCafeAdapter
import kotlinx.android.synthetic.main.activity_add_travel.view.*
import kotlinx.android.synthetic.main.activity_dreview.*
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.fragment_main.*

class DReviewActivity : AppCompatActivity() {

    lateinit var db: Firestore

    var t_where = ""
    var t_when = ""
    var t_who = -1
    var t_id = -1
    var t_cost =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dreview)
        init()
        setData()
    }

    fun init() {

        db = Firestore.create(applicationContext)
        var tabLayer: TabLayout? = null

        tabLayer = findViewById(R.id.dreview_tab)

        if (tabLayer!!.tabCount == 0) {
            //탭 추가
            tabLayer?.addTab(tabLayer!!.newTab().setText("기본 정보"))
            tabLayer?.addTab(tabLayer!!.newTab().setText("일정"))
            tabLayer?.addTab(tabLayer!!.newTab().setText("지참 물품 리스트"))
            tabLayer?.addTab(tabLayer!!.newTab().setText("여행 리뷰").setIcon(R.drawable.ic_flight_land_black_24dp))
        }


        val adapter = DTabAdapter(supportFragmentManager, tabLayer!!.tabCount)
        // 이게 문제였다니
        dreview_frame.adapter = adapter
        dreview_frame.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayer))
        tabLayer!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                dreview_frame.currentItem = tab.position
            }
        })
        dreview_frame!!.adapter!!.notifyDataSetChanged()
    }

    fun setData(){
        val i = intent.getIntExtra("t_id",-1)
        db!!.db!!.collection("Travel").whereEqualTo("t_id",i).get()
            .addOnSuccessListener {
                var a = it.documents

                for(i in a){
                    t_where = i.get("t_where").toString()
                    t_when = i.get("t_when").toString()
                    t_who = i.get("t_who").toString().toInt()
                    t_id = i.get("t_id").toString().toInt()
                    t_cost = i.get("t_cost").toString()
                    Log.e("드로어블","$t_where\t$t_when\t$t_who\t$t_id\t$t_cost")
                }
                initFav()
            }
    }

    fun initFav(){
        db!!.db!!.collection("User").document(MainActivity.User.toString()).get()
            .addOnCompleteListener {
                val a = it?.result!!.data?.get("fav") as ArrayList<Long>
                Log.e("배열값","$a")
                var isfav = false
                for(k in a){
                    if(k.toInt() == t_id){
                        isfav = true
                        break
                    }
                }
                DReviewFrag1.start(t_where,t_when,t_who,t_cost, isfav)
            }
    }
}

