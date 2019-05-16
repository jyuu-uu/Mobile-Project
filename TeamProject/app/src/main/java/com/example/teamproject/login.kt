package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*

class login: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        init()
    }

    fun init(){
        login.setOnClickListener{

            val id = identifier.text.toString()
            val ps = password.text.toString()

            // 이부분에 if문으로 파이어폭스 접근
            //아이디 확인

            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("User",id) //현재 사용중인 유저가 누구인가??
            startActivity(intent)
        }
    }

}