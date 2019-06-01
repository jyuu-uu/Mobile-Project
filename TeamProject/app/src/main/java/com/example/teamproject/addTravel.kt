package com.example.teamproject


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
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
                val sqlite = SQLite(this,"Login")
                // 데이터베이스를 오픈
                sqlite.openDatabase("USER")
                val auto = sqlite.AutoLogin()

                var newtravel=MyTravel(travels.size+1,auto!![0],editWhere.text.toString(),period.toString(),editPeople.text.toString().toInt(),editMoney.text.toString()+'원')

                //======================================================================================
                val db = FirebaseFirestore.getInstance()
                //데이터준비
                if (db != null) {
                    var new: MutableMap<String, Any>? = null
                    new = mutableMapOf()
                    new["t_id"] = newtravel.tno
                    new["u_id"] = newtravel.uno
                    new["t_where"] = newtravel.where
                    new["t_when"] = newtravel.period
                    new["t_who"] = newtravel.people
                    new["t_cost"] = newtravel.money
                    // Add a new document with a generated ID

//        val newCount = String.format("%03d", count + 1)
                    db!!.collection("Travel").document("travel"+ (travels.size+1).toString())
                        .set(new)
                        .addOnSuccessListener { Log.e("database", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.e("database", "Error writing document") }
                    //travels.add(newtravel)
                }
                Toast.makeText(this, travels.size.toString(),Toast.LENGTH_LONG).show()
                //======================================================================================

                //Toast.makeText(this,"여행이 추가되었습니다.",Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }


}
