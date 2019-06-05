package com.example.teamproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class DTabAdapter(fm: FragmentManager,val num:Int): FragmentPagerAdapter(fm){
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
            0-> return DReviewFrag1()
            1-> return DReviewFrag2()
            2-> return DReviewFrag1()
            3-> return DReviewFrag2()
        }
        return null
    }
}