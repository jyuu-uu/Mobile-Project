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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
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

    }
    fun setItem(){
        var i=0
        schedule.clear()
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