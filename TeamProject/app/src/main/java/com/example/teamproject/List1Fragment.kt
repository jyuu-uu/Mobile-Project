package com.example.teamproject


import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
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
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_list1.*
import kotlinx.android.synthetic.main.fragment_list1_card.*
import android.widget.EditText
import android.content.DialogInterface
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class List1Fragment : Fragment() {
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

        textView15.setText(travels[index].where.toString()+"의 일정")

        readData()
        setItem()
        //textView15.setText(schedules[1].todo)
        //val icon=activity!!.findViewById<ImageView>(R.id.listicon)
        add_schedule.setOnClickListener {
            val dialogView=layoutInflater.inflate(R.layout.add_schedule_dialog, null)
            val dialogwhat = dialogView.findViewById<EditText>(R.id.schedule_what)
            val dialogwhen = dialogView.findViewById<EditText>(R.id.schedule_when)
            val ad = AlertDialog.Builder(context)
            ad.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    if(dialogwhat.text.toString()!=""&&dialogwhen.text.toString()!=""){
                        schedules.add(schedule(schedules.size,index,dialogwhen.text.toString(),dialogwhat.text.toString(),false))
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
        val db = FirebaseFirestore.getInstance()
        var s_id:Int
        var t_id:Int
        var s_todo:String
        var s_time:String
        var s_alarm:Boolean
        db!!.collection("Schedule")
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
                            s_id=doc.get("s_id").toString().toInt()
                            s_todo=doc.get("s_todo").toString().toInt().toString()
                            s_time=doc.get("s_time").toString().toInt().toString()
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
            if(i==index){
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

    fun setpush(){
        var notificationManager: NotificationManager =context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var pushintent = Intent(context, MainActivity::class.java)
        var builder = Notification.Builder(context)
        pushintent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var pendingNotificationIntent =
            PendingIntent.getActivity(context, 0, pushintent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setTicker("HETT").setWhen(System.currentTimeMillis())
            .setNumber(1).setContentTitle("푸쉬 제목").setContentText("푸쉬내용")
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true)
        val notification = builder.build()
        notificationManager.notify(2, notification)
        //notificationManager.notify(1, builder.build()) // Notification send
    }

}