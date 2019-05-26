package com.example.teamproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.login_layout.*

class login: AppCompatActivity() {

    val code = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        init()

        val intent = Intent(this, LoadingActivity::class.java)
        startActivityForResult(intent, code)
        // 해당 부분 스레드로 구현 필요

    }

    fun init() {
        login.setOnClickListener {
            // 로그인 버튼
            val id = identifier.text.toString()
            val pw = password.text.toString()
            // 이부분에 if문으로 파이어폭스 접근
            //아이디 확인

            if(true)
                In(id) // 있다면 정보를 넘기고 종료
            else{ // 아이디가 없으면 다시 시도
                val space= findViewById<EditText>(R.id.password)
                space.setText(null) // 비밀번호 칸은 초기화
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        System.runFinalization()
        System.exit(0)
        // 현재 로그인 창은 스플래쉬로 띄워진 상태
        // 따라서 로그인에서 뒤로버튼을 누르면 메인포함, 전부 없애야 함

//        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 로딩창에서 가능한 정보를 모두 불러올 거고, 정보를 받는 경우, 이쪽에서 결정
        if (resultCode == code) {
            if (resultCode == Activity.RESULT_OK) { // 결과로 보내준 상태가 OK 코드면
                val id = data?.getStringExtra("id") //값을 받아옴
                val pw = data?.getStringExtra("pw")
                Log.e("login","$id and $pw")
                In(id!!) // 받아온 정보를 기반으로 자동로그인
            }
            else{
                Log.e("login","need login")
            }
        }
    }

    fun In(i_id:String){ //정보넘기고 종료함수
        val s = Intent()
        s.putExtra("id", i_id)
        setResult(Activity.RESULT_OK,s)
        finish()
    }
}