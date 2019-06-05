package com.example.teamproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dreview_frag1.view.*

class DReviewFrag1 : Fragment() {

    companion object{
        lateinit var v:View

        fun start(t_where:String,t_when:String,t_who:Int,t_cost:String,isfav:Boolean){
            v.dreview_where.text = t_where
            v.dreview_when.text = t_when
            v.dreview_who.text = t_who.toString() + " 명"
            v.dreview_cost.text = t_cost
            v.dreview_isfav.isChecked = isfav
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_dreview_frag1, container, false)
        return v
    }
}
