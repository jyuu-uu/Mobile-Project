package com.example.teamproject

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LoadingActivity : AppCompatActivity() {

    var splashThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

//        splashThread = object : Thread() {
//            override fun run() {
//                try {
//                    synchronized(this) {
//                        // Wait given period of time or exit on touch
//
//                        startLoading()
//                    }
//                } catch (ex: InterruptedException) {
//                }
//
//               // finish()
//                // Run next activity
//            }
//        }
//        (splashThread as Thread).start()

        startLoading()

        val sqlite = SQLite(this)
        sqlite.openDatabase("USER")
        // 데이터베이스를 오픈
        val auto = sqlite.AutoLogin()



        if(auto != null){
            // 뭔가 값이 반환됬다면, 자동로그인
            val s = Intent()
            s.putExtra("id", auto[0])
            s.putExtra("pw", auto[1])
            setResult(Activity.RESULT_OK,s)
            // 보내줄 응답결과와 정보를 보낼 intent를 세팅
        }
        else{
            val s = Intent()
            setResult(Activity.RESULT_CANCELED,s)
        }

 //       (this as Object).wait(10000)
//        splashThread?.let {
//            synchronized(it) {
//                (splashThread as Object).notifyAll()
//            }
//        }
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable { finish() }, 2000)
//        (this as Object).wait(-1)
    }
}
