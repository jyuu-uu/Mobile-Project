package com.example.teamproject


import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_list.*
var index=-1
lateinit var travels:MutableList<MyTravel>
lateinit var schedules:MutableList<schedule>
class ListActivity : AppCompatActivity() {
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        init()
        adapter.notifyDataSetChanged()
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
        schedules= mutableListOf()
        travels= mutableListOf()
        readData()
        addListener()
        addTravel()
        adapter=MyAdapter(this,R.layout.list_row,ArrayList(travels))
        listView.adapter=adapter

    }
    fun readData(){

        val db = FirebaseFirestore.getInstance()
        var t_id:Int
        var u_id:Int
        var t_when:String
        var t_where:String
        var t_who:Int
        var t_cost:String
        db!!.collection("Travel")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, e: FirebaseFirestoreException?) {
                    Log.e("database", "aa")
                    if (e != null) {
                        Log.e("database", "database Listen failed.2")
                        return
                    }
                    val count = value?.size()
                    var flag = false
                    // list.clear()//일딴 초기화 해줘야 한다. 안 그럼 기존 데이터에 반복해서 뒤에 추가된다.
                    if (value==null)Log.e("database", "bb")
                    if (value != null) {

                        Log.e("database", "cc")
                        for (doc in value) {
                            Log.e("database", "$doc 읽는 중2")
                            t_id=doc.get("t_id").toString().toInt()
                            u_id=doc.get("u_id").toString().toInt()
                            t_when=doc.get("t_when").toString()
                            t_where=doc.get("t_where").toString()
                            t_who=doc.get("t_who").toString().toInt()
                            t_cost=doc.get("t_cost").toString()
                            travels.add(MyTravel(t_id,u_id,t_where,t_when,t_who,t_cost))
                        }
//                     adapter.notifyDataSetChanged()
                    }
                }
            })
//        travels.add(MyTravel(0,-1,"미국","2017년 1월 23일 ~ 2017년 2월 11일",2,"500만원"))
//        travels.add(MyTravel(1,-1,"호주","2018년 7월 15일 ~ 2018년 7월 28일",2,"300만원"))
//        schedules.add(schedule(0,0,"1시","자유의여신상",false))
//        schedules.add(schedule(1,1,"5시","동물원",false))
    }
    fun addListener(){
        //여행 리스트 중 하나 클릭하면 그 여행 정보랑 준비물리스트 보여주는 창으로 넘어감
        listView.setOnItemClickListener { parent, view, position, id ->
            val w=parent.getItemAtPosition(position) as MyTravel    //클릭한 여행
            var intent0 = Intent(this, ListDetailActivity::class.java)
            intent0.putExtra("tno",w.tno)
            startActivity(intent0)
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