package com.example.teamproject

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
    var data = ArrayList<MyCafe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dreview)
        init()
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
//            content.clearOnPageChangeListeners()
        //          (tabLayer as FragmentPagerAdapter).notifyDataSetChanged()

    }
}

