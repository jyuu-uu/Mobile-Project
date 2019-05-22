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
            0-> return MainFragment()
            1-> return MainFragment()
            2-> return ReviewFragment()
        }
        return null
    }
}