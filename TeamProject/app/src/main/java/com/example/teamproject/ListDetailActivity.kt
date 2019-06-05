package com.example.teamproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetailActivity : AppCompatActivity() {
    lateinit var tabLayout:TabLayout
    private val adapter by lazy { TablistAdapter(supportFragmentManager,index) }
    var index = -1
    //var tno=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        var i=intent
        index=i.getIntExtra("tno",-1)
        //Toast.makeText(this, index.toString(),Toast.LENGTH_SHORT).show()
        init()

    }

    fun init(){
        tabLayout = findViewById(R.id.tabLayout)
        pager.adapter = ListDetailActivity@adapter
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

                tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_1_black)
                tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_2_black)

                when(position) {

                    0   ->    tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_1)
                    1   ->    tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_2)
                }
            }
        })

        // 탭 레이아웃에 뷰페이저 연결
        tabLayout.setupWithViewPager(pager)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_1)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_2_black)

    }
}
