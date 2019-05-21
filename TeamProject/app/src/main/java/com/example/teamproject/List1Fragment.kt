package com.example.teamproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list1.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class List1Fragment : Fragment() {
    lateinit var schedules:MutableList<schedule>
    lateinit var schedule:MutableList<schedule>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }
    fun init(){
        travels= mutableListOf()
        readData()
        setItem()
    }

    fun readData(){
        schedules.add(schedule(0,"1시","자유의여신상"))
        schedules.add(schedule(1,"5시","동물원"))
    }
    fun setItem(){
        for(i in 0..schedules.size){
            if(schedules[i].tno==index){
                schedule.add(schedules[i])
            }
        }
    }
}
