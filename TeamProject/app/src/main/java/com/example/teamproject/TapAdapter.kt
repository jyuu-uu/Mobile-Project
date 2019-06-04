package com.example.teamproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager,val num:Int): FragmentPagerAdapter(fm){


    override fun getCount(): Int {
        return num
    }

    override fun getItemPosition(`object`: Any): Int {
//        return super.getItemPosition(`object`)
        return POSITION_NONE;
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment? {
        when(position){
            0-> return ReviewFragment()
            1-> return main_second()
        }
        return null
    }
}