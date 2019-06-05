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
        //alarm()

        Log.e("로딩 창 생성","로딩창")
//        val sqlite2 = SQLite(this,"Alarm")
//        sqlite2.openDatabase("USER")
//        sqlite2.alarm(this)

        val sqlite = SQLite(this,"Login")
        // 데이터베이스를 오픈
        sqlite.openDatabase("USER")
        //sqlite.dropTable("Login")
        sqlite.readAlarm(this)
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

}
