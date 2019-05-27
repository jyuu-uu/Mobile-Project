package com.example.teamproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager,val num:Int): FragmentPagerAdapter(fm){

    override fun getCount(): Int {
        return num
    }

    override fun getItem(position: Int): Fragment? {
        when(position){
            0-> return main_first()
            1-> return main_second()
//            2-> return ReviewFragment()
        }
        return null
    }
}