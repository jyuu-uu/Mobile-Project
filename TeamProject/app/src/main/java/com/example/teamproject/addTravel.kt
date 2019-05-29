package com.example.teamproject


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_travel.*

class addTravel : AppCompatActivity() {
    lateinit var period:String
    lateinit var period1:String
    lateinit var period2:String
    var check1=false
    var check2=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_travel)
        period()
        init()
    }
    fun period(){
        val click = findViewById(R.id.date1) as TextView
        click.setOnClickListener {
            showDialog(1)
        }
        val click2 = findViewById(R.id.date2) as TextView
        click2.setOnClickListener {
            showDialog(2)
        }
    }

    override fun onCreateDialog(id: Int): Dialog {
        when(id){
            1->
                return DatePickerDialog(
                    this, // 현재화면의 제어권자
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        period1=year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
                        Toast.makeText(
                            applicationContext,
                            period1,
                            Toast.LENGTH_SHORT
                        ).show()
                        date1.setText(period1)
                        check1=true
                    }, // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                    //    호출할 리스너 등록
                    2019, 5, 25
                )
            2->
                return DatePickerDialog(
                    this, // 현재화면의 제어권자
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        period2=year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
                        Toast.makeText(
                            applicationContext,
                            period2,
                            Toast.LENGTH_SHORT
                        ).show()
                        date2.setText(period2)
                        check2=true
                    }, // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                    //    호출할 리스너 등록
                    2019, 5, 25
                )
        }
        return super.onCreateDialog(id)

    }

    fun init(){
        cancel.setOnClickListener {
            finish()
        }
        add()

    }

    fun add(){
        add.setOnClickListener {
            if(editWhere.text.toString()==""||editMoney.text.toString()==""||editPeople.text.toString()==""||check1==false||check2==false){
                Toast.makeText(this,"빈칸을 모두 채워주세요.",Toast.LENGTH_LONG).show()
            }else{
                period=period1+"~"+period2
                travels.add(MyTravel(travels.size,"check",editWhere.text.toString(),period.toString(),editPeople.text.toString().toInt(),editMoney.toString()))
                Toast.makeText(this,"여행이 추가되었습니다.",Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }


}
