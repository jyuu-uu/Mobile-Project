package com.example.teamproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.myreportaftertravel.MyCafe
import com.example.myreportaftertravel.MyCafeAdapter
import kotlinx.android.synthetic.main.activity_review.*
import java.util.*

class ReviewFragment: Fragment() {

    var readfile = false
    var data:ArrayList<MyCafe> = ArrayList()
    lateinit var adapter:MyCafeAdapter
    lateinit var auto_adapter: ArrayAdapter<String>
    val list = arrayListOf("ababab","babca","cabse","dsefsw","esesw")

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }
    fun init(){
        initLayout()
        initSwipe()
        if(readfile == false)   readFile()
        rcClick(review_listView)
    }
    fun rcClick(v:View){    //recycleview 에서 클릭한거 페이지 띄우기
    }

    fun initSwipe(){
        val simpleItemTouchCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                adapter.moveItem(p1.adapterPosition, p2.adapterPosition)
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                adapter.removeItem(p0.adapterPosition)
            }
        }   //객체 생성
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(review_listView)
    }
    fun initLayout(){
        val layoutManager = LinearLayoutManager(super.getContext(), LinearLayoutManager.VERTICAL, false)
        //val layoutManager = GridLayoutManager(this, 3)
        //val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.GAP_HANDLING_NONE)
        review_listView.layoutManager = layoutManager
        adapter = MyCafeAdapter(data)
        review_listView.adapter = adapter
        save.setOnClickListener {
            var point_country = -1;
            when(country.text.toString()){
                "태국"->{
                    for(i in data){
                        if(i.country == "태국")
                            point_country = i.hashCode()
                    }
                }
                "미국"->{
                    for(i in data){
                        if(i.country == "미국")
                            point_country = i.hashCode()
                    }
                }
            }
            adapter.moveItem(0, point_country)
        }
    }

    fun readFile(){ //words, array 초기화
        var scan = Scanner(resources.openRawResource(R.raw.test_text))
        while(scan.hasNextLine()){
            val str = scan.nextLine()
            val str_result = str.split(',')
            data.add(0, MyCafe(str_result[0], str_result[5], str_result[1]))
        }
        readfile = true
        scan.close()
    }
}