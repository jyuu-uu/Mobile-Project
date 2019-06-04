package com.example.teamproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.fragment_country.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

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
        Log.e("순서","뷰 크리에잇")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
//        Log.e("순서","액티비티크리에이")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        Log.e("순서","어테치")
    }

    //private var tabLayer: TabLayout?= null

    fun init() {
        if (activity != null) {
            var tabLayer: TabLayout? = null

            tabLayer = activity?.findViewById(R.id.layout_tab)

            if (tabLayer!!.tabCount ==0) {
                //탭 추가
                tabLayer?.addTab(tabLayer!!.newTab().setText("리뷰 검색"))
                tabLayer?.addTab(tabLayer!!.newTab().setText("Tab 2"))
//                tabLayer?.addTab(tabLayer!!.newTab().setText("리뷰"))
            }


            val adapter = TabAdapter(childFragmentManager, tabLayer!!.tabCount)
            // 이게 문제였다니
            Log.e("순서","어댑터 $adapter")
            content.adapter = adapter
            content.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayer))
            tabLayer!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab?) {}
                override fun onTabUnselected(p0: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab) {
                    content.currentItem = tab.position
                }
            })
            content!!.adapter!!.notifyDataSetChanged()
//            content.clearOnPageChangeListeners()
  //          (tabLayer as FragmentPagerAdapter).notifyDataSetChanged()

        }
        else
            Log.e("main_first", "액티비티가 안붙었어요")
    }
}