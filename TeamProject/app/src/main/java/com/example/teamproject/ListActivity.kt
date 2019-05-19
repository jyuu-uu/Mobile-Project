package com.example.teamproject


import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*

lateinit var travels:MutableList<MyTravel>

class ListActivity : AppCompatActivity() {
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        init()
        val swipe=findViewById(R.id.swiperefresh) as SwipeRefreshLayout
        swipe.setOnRefreshListener {
            setItem(ArrayList(travels))
            adapter = MyAdapter(this, R.layout.list_row, ArrayList(travels))
            listView.adapter = adapter
            swipe.setRefreshing(false);
        }
    }
    fun init(){
        Toast.makeText(this,"a",Toast.LENGTH_SHORT).show()
        travels= mutableListOf()
        readData()
        addListener()
        addTravel()
        adapter=MyAdapter(this,R.layout.list_row,ArrayList(travels))
        listView.adapter=adapter

    }
    fun readData(){
        travels.add(MyTravel(1,"미국","2017년 1월 23일 ~ 2017년 2월 11일",2,"500만원"))
        travels.add(MyTravel(2,"호주","2018년 7월 15일 ~ 2018년 7월 28일",2,"300만원"))

    }
    fun addListener(){
        //여행 리스트 중 하나 클릭하면 그 여행 정보랑 준비물리스트 보여주는 창으로 넘어감
        listView.setOnItemClickListener { parent, view, position, id ->
            val w=parent.getItemAtPosition(position) as MyTravel    //클릭한 여행
            //Toast.makeText(this,w.period,Toast.LENGTH_SHORT).show() //걍 잘받아졌는지 확인하려고..
        }
    }
    fun addTravel(){
        addBtn.setOnClickListener {
            var intent=Intent(this, addTravel::class.java)
            startActivity(intent)
        }
    }
    fun setItem(list:ArrayList<MyTravel>){
        travels.clear()
        travels.addAll(list)
    }

}
