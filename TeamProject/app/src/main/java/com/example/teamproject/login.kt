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
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("User","")
            startActivity(intent)
        }
    }

}