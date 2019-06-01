package com.example.teamproject

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        // static 메소드에 해당하는 기능을 수행
        fun newFragment():MainFragment{
            // 값을 받아서 지정하고,
            // 해당 값으로 초기화된 fragment를 반환

            val mainFrag = MainFragment()
            // 값 넣어줄거 여기서 처리

            return mainFrag // 해당 객체 반환
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private var tabLayer: TabLayout?= null

    fun init() {
        if (activity != null) {
            tabLayer = activity?.findViewById(R.id.layout_tab)
            tabLayer?.addTab(tabLayer!!.newTab().setText("Tab 1"))
            tabLayer?.addTab(tabLayer!!.newTab().setText("Tab 2"))
            tabLayer?.addTab(tabLayer!!.newTab().setText("리뷰"))
            tabLayer?.addTab(tabLayer!!.newTab().setText("Map"))
            //탭 추가

            val adapter = TabAdapter(activity!!.supportFragmentManager, tabLayer!!.tabCount)
            content.adapter = adapter

            content.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayer))

            tabLayer!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab?) {}
                override fun onTabUnselected(p0: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab) {
                    content.currentItem = tab.position
                }
            })

        }
        else
            Log.e("main_first", "액티비티가 안붙었어요")
    }
}