package com.example.teamproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TablistAdapter(fm: FragmentManager,var t_num:Int) : FragmentStatePagerAdapter(fm)  {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0-> List1Fragment.start(t_num)
            1-> List2Fragment.start(t_num)
            else-> null
        }
    }
}