package com.example.teamproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.google.firestore.v1.FirestoreGrpc
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    lateinit var db:Firestore
    var id:String? = null
    var pw:String? = null
    var check1:Boolean=false
    var check2:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        db = Firestore.create(applicationContext)

        initText()
        initBtn()
    }

    fun initText(){
        j_id.addTextChangedListener(object: TextWatcher {
            // text의 변화를 감지하는 함수를 달아주는 함수로, textWatcher를 인자로 필요로 함
            // 텍스트 와쳐는 인터페이스이므로, 객체 생성이 불가능 (추상 메소드)
            // so, body 정보를 모두 채워서 묵시적 객체 생성할 예정 (오브젝트 클래스 사용)
            // object:를 붙인 후, alt+엔터

            override fun afterTextChanged(s: Editable?) {
                // 텍스트에 변화가 생겼을 경우
                val str = s.toString() // s 객체의 값을 string 으로 갖고옴
                check1 = str.isNotEmpty()
                // 문자열이 비어있지 않으면 button을 활성화 (true)
                // 문자열이 비어있으면, button 비활성화 (false)
                // str의 length 값을 조건문으로 활성화 방법도 가능

                checkInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        j_pw.addTextChangedListener(object:TextWatcher{
            // text의 변화를 감지하는 함수를 달아주는 함수로, textWatcher를 인자로 필요로 함
            // 텍스트 와쳐는 인터페이스이므로, 객체 생성이 불가능 (추상 메소드)
            // so, body 정보를 모두 채워서 묵시적 객체 생성할 예정 (오브젝트 클래스 사용)
            // object:를 붙인 후, alt+엔터

            override fun afterTextChanged(s: Editable?) {
                // 텍스트에 변화가 생겼을 경우
                val str = s.toString() // s 객체의 값을 string 으로 갖고옴
                check2 = str.isNotEmpty()
                // 문자열이 비어있지 않으면 button을 활성화 (true)
                // 문자열이 비어있으면, button 비활성화 (false)
                // str의 length 값을 조건문으로 활성화 방법도 가능

                checkInput()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun initBtn(){
        j_join.setOnClickListener {
            id = j_id.text.toString()
            pw = j_pw.text.toString()
            Log.e("join","$id\t$pw")
            // 아이디 중복
            // 검사 실시
            findData()

        }
        j_cancel.setOnClickListener {
            finish() //종료
        }
    }

    fun checkInput(){
        j_join.isEnabled = (check1 && check2) // 버튼활성화
    }

    fun findData(){
        if(db != null){
            val isExist = db!!.db!!.collection("User").document(id!!).get()
                .addOnSuccessListener {
                    val res = it.get("doc")//a.result//!!.get("doc")
                    Log.e("firebase","$it\n$res")
                    val Exist = if(res != "null") true else false
                    if(!Exist){
                        db!!.addData(id!!,pw!!)
                        finish()
                    }
                    else
                        Toast.makeText(this,"이미 존재하는 아이디입니다",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {  }

        }
    }
}

