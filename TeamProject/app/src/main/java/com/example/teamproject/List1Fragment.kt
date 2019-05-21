package com.example.teamproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*
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
    lateinit var adapter:CardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        schedule= mutableListOf()
        schedules= mutableListOf()
        init()
        initLayout()
        initSwipe()
    }
    fun init(){
        textView15.setText(travels[index].where.toString()+"의 일정")

        readData()
        setItem()
        //textView15.setText(schedules[1].todo)
    }

    fun readData(){
        schedules.add(schedule(0,"1시","자유의여신상"))
        schedules.add(schedule(1,"5시","동물원"))
    }
    fun setItem(){
        var i=0
        while (i<schedules.size){
            if(schedules[i].tno==index){
                schedule.add(schedules[i])
            }
            i++
        }
    }
    fun initSwipe(){
        val simpleItemTouchCallback=object:ItemTouchHelper.SimpleCallback(UP or DOWN, RIGHT){
            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder
            ): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                adapter.moveitem(p1.adapterPosition, p2.adapterPosition)
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                adapter.removeitem(p0.adapterPosition)
            }

        }   //객체 생성

        val itemTouchHelper=ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(list1View)


    }

    fun initLayout(){
        var layoutManager= LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        list1View.layoutManager=layoutManager
        adapter=CardAdapter(ArrayList(schedule))
        list1View.adapter=adapter
//        spinner_wifi.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//            }
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////                if(position==0)
////                    wifi=true
////                else
////                    wifi=false
//                wifi = position==0
//            }
//        }//spinner
//        save.setOnClickListener {
//            if(name.length()>0&&phoneNum.length()>0){   //한글자라도입력
//                data.add(0,MyCafe(name.text.toString(),phoneNum.text.toString(),wifi))  //0->새로추가되면 맨위
//                adapter.notifyDataSetChanged()
//            }
//        }
    }


}


