package com.example.teamproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter.POSITION_NONE

class DTabAdapter(fm: FragmentManager,val num:Int,val t_id:Int,val t_where:String,val t_when:String,
                  val t_who:Int,val t_cost:String, val isfav :Boolean): FragmentPagerAdapter(fm){
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
            0-> return DReviewFrag1.start(t_where,t_when,t_who,t_cost, isfav,t_id)
            1-> return DReviewFrag2.start(t_id)
            2-> return DReviewFrag3()
            3-> return DReviewFrag4()
        }
        return null
    }
}