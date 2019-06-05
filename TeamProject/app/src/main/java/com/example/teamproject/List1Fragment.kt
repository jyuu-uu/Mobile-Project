package com.example.teamproject


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_list1.*
import kotlinx.android.synthetic.main.fragment_list1_card.*
import android.content.DialogInterface
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlin.collections.ArrayList


class List1Fragment : Fragment(){
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
        Log.e("created","created")
        super.onActivityCreated(savedInstanceState)
        schedule= mutableListOf()
        Log.e("index", index.toString())
        for (i in 0..travels.size){
            if(travels[i].tno==index){
                index=i
                break
            }
        }
        init()
        initLayout()
        initSwipe()
        val swipe=activity!!.findViewById(R.id.swiperefresh) as SwipeRefreshLayout
        swipe.setOnRefreshListener {
            setItem()
            initLayout()
            swipe.setRefreshing(false);
        }
    }
    fun init(){
        Log.e("index", index.toString())
        Log.e("index", travels[index].tno.toString())
        Log.e("index", travels.size.toString())
        textView15.setText(travels[index].where+"의 일정")

        readData()
        setItem()
        //textView15.setText(schedules[1].todo)
        //val icon=activity!!.findViewById<ImageView>(R.id.listicon)
        val ad = AlertDialog.Builder(context)
        add_schedule.setOnClickListener {
            val dialogView=layoutInflater.inflate(R.layout.add_alarm, null)
            val dialogwhat = dialogView.findViewById<EditText>(R.id.schedule_what)
            val datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)
            val timePicker=dialogView.findViewById<TimePicker>(R.id.time_picker)
            val dialogtext1 = dialogView.findViewById<TextView>(R.id.textv1)
            val dialogtext2 = dialogView.findViewById<TextView>(R.id.textv2)
            dialogtext1.setText("일정 제목")
            dialogtext2.setText("일정 시간")
            var datetime=datePicker.year.toString()+"/"+datePicker.month+"/"+datePicker.dayOfMonth+"/"+timePicker.hour.toString()+":"+timePicker.minute

            ad.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    if(dialogwhat.text.toString()!=""){
                        val db = FirebaseFirestore.getInstance()
                        //데이터준비
                        if (db != null) {
                            var new: MutableMap<String, Any>? = null
                            new = mutableMapOf()
                            new["s_id"] = schedules.size+1
                            new["s_todo"] =dialogwhat.text.toString()
                            new["s_time"] = datetime.toString()
                            new["s_alarm"] =false
                            new["t_id"] =travels[index].tno

                            // Add a new document with a generated ID

//        val newCount = String.format("%03d", count + 1)
                            db!!.collection("Schedule").document("schedule"+ (schedules.size+1).toString())
                                .set(new)
                                .addOnSuccessListener { Log.e("database", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.e("database", "Error writing document") }
                        }

                        //schedules.add(schedule(schedules.size,travels[index].tno,dialogwhen.text.toString(),dialogwhat.text.toString(),false))
                        Toast.makeText(activity,"일정 추가 완료! 새로고침하려면 밑으로 스와이프",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity,"일정 추가 실패",Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()

        }

    }

    fun readData(){
        schedules.clear()
        Log.e("readData","readdata")
        val db = FirebaseFirestore.getInstance()
        var s_id:Int
        var t_id:Int
        var s_todo:String
        var s_time:String
        var s_alarm:Boolean
        db!!.collection("Schedule")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, e: FirebaseFirestoreException?) {
                    schedules.clear()
                    Log.e("database", "schedulea")
                    if (e != null) {
                        Log.e("database", "database Listen failed.2")
                        return
                    }
                    val count = value?.size()
                    var flag = false
                    // list.clear()//일딴 초기화 해줘야 한다. 안 그럼 기존 데이터에 반복해서 뒤에 추가된다.
                    if (value==null)Log.e("database", "scheduleb")
                    if (value != null) {
                        Log.e("database", "schedulec")
                        for (doc in value) {
                            Log.e("database", "$doc 읽는 중2")
                            t_id=doc.get("t_id").toString().toInt()
                            s_id=doc.get("s_id").toString().toInt()
                            s_todo=doc.get("s_todo").toString()
                            s_time=doc.get("s_time").toString()
                            s_alarm=doc.get("s_alarm").toString().toBoolean()
                            schedules.add(schedule(s_id,t_id,s_time,s_todo,s_alarm))
                        }
//                     adapter.notifyDataSetChanged()
                    }
                }
            })
    }
    fun setItem(){
        var i=0
        schedule.clear()
        while (i<schedules.size){
            if(schedules[i].tno== travels[index].tno){
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
        adapter=CardAdapter(context!!,ArrayList(schedule))
        list1View.adapter=adapter
    }
}