package com.example.teamproject

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TablistAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm)  {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0-> List1Fragment()
            1-> List2Fragment()
            else-> null
        }
    }
}