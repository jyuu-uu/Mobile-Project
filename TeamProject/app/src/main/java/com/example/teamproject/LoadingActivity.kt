package com.example.teamproject

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list1_card.*
import java.util.*

class LoadingActivity : AppCompatActivity() {

    lateinit var s : Intent
    lateinit var my_intent :Intent
    lateinit var pendingIntent: PendingIntent
    lateinit var alarm_manager: AlarmManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        my_intent=Intent(this, Receiver::class.java)
        alarm()

        Log.e("로딩 창 생성","로딩창")
        val sqlite = SQLite(this,"Login")
        // 데이터베이스를 오픈
        sqlite.openDatabase("USER")
        val auto = sqlite.AutoLogin()

        startLoading()

        if(auto != null){
            // 뭔가 값이 반환됬다면, 자동로그인
            s = Intent(this,MainActivity::class.java) // 바로 로그인시키면 됨
            s.putExtra("success",true)
            s.putExtra("id", auto[0])
            s.putExtra("pw", auto[1])
//            setResult(Activity.RESULT_OK,s)
            // 보내줄 응답결과와 정보를 보낼 intent를 세팅
        }
        else{
            s = Intent(this,MainActivity::class.java)
            s.putExtra("success",false) //실패했음을 의미
        }
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivity(s)
            finish() }, 1500)
    }
    fun alarm(){
        alarm_manager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val sqlite = SQLite(this,"Alarm")
        sqlite.openDatabase("USER")
        my_intent.putExtra("state","alarm on");
        pendingIntent = PendingIntent.getBroadcast(this, 0, my_intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        var readalarm= sqlite.readAlarm()
        if(readalarm==null){
            return
        }
        var id=readalarm[0]
        var date=readalarm[1]
        var time=readalarm[2]
        var todo=readalarm[3]
        var what=readalarm[4]
        my_intent.putExtra("a_id",id)
        my_intent.putExtra("todo",todo)
        my_intent.putExtra("what",what)
        Log.e("id",id)

        var separate1 = date.split("-") //년-월-일 배열
        var separate2 = time.split(":") //시-분 배열

        val calendar = Calendar.getInstance()
        //알람시간 calendar에 set해주기
        //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 20, 27, 0)
        calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), separate2[0].toInt(), separate2[1].toInt(), 0)
        //calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), 16, 13, 0)
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)

        //sqlite.dropTable("Alarm")
//        sqlite.deleterow(id.toInt(),"Alarm")
//        alarm()
        //Log.e("alarm delete",id+" alarm deleted")

////        val calendar = Calendar.getInstance()
////        //알람시간 calendar에 set해주기
////        //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 20, 27, 0)
////        calendar.set(alarm_datepicker.year, alarm_datepicker.month, alarm_datepicker.dayOfMonth, alarm_timepicker.hour, alarm_timepicker.minute, 0)
////        var sqlite = SQLite(context,"Alarm")
////        sqlite.openDatabase("USER")
////        //val dateFormat = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
////        //val timeFormat = SimpleDateFormat("hh:mm", java.util.Locale.getDefault())
////        //val date = dateFormat.parse(alarm_datepicker.year.toString()+"-"+alarm_datepicker.month+"-"+alarm_datepicker.dayOfMonth)
////        //val time = dateFormat.parse(alarm_timepicker.hour.toString()+":"+alarm_timepicker.minute+":00")
////
////        //var date=alarm_datepicker.year.toString()+"-"+alarm_datepicker.month+"-"+alarm_datepicker.dayOfMonth
////        //var time=alarm_timepicker.hour.toString()+":"+alarm_timepicker.minute//+":00"
////        Log.e("datepicker",date)
////        Log.e("timepicker",time)
////        sqlite.insertData(date,time,items.get(position).todo,dialogwhat.text.toString())
////        //sqlite.dropTable("Alarm")
////        //	db date type 2008-11-11 / time hh:mm:ss
////        // 알람셋팅
//        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
    }
}
