package com.example.teamproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import android.provider.SyncStateContract.Helpers.update
import com.google.firestore.v1.WriteResult
import com.google.firebase.firestore.DocumentReference



class Firestore {

    var activity: Context? = null
    var db: FirebaseFirestore? = null
    companion object{
        fun create(con: Context):Firestore{
            var database = Firestore()
            database.db = FirebaseFirestore.getInstance()
            database.activity = con
            return database
        }


    }

    fun addData(_id:String,_pw:String) { // 회원계정 버전
        //데이터준비
        if (db != null) {
            var user: MutableMap<String, String>? = null
            user = mutableMapOf()
            user["u_id"] = _id
            user["u_pw"] = _pw

//        val newCount = String.format("%03d", count + 1)
            db!!.collection("User").document(_id)
                .set(user)
                .addOnSuccessListener {
                    Toast.makeText(activity,"회원가입에 성공하였습니다",Toast.LENGTH_SHORT).show()
                    Log.e("database", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.e("database", "Error writing document") }
        }
    }

    fun findData(_id: String,_pw: String){
        if(db != null){
           val isExist = db!!.collection("User").document(_id).get()
               .addOnSuccessListener {
                    val res = it.get("doc")//a.result//!!.get("doc")
                    Log.e("firebase","$it\n$res")
                    val Exist = if(res != "null") true else false
                    if(Exist){
                        addData(_id,_pw)
                    }
                    else
                        Toast.makeText(activity,"이미 존재하는 아이디입니다",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {  }

        }
    }

    fun deleteData(_id:String,flag:Int = 0) { // 회원계정 버전
        //데이터준비
        if (db != null) {

            when(flag){
                0->{
                    db!!.collection("User").document(_id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(activity,"계정 탈퇴에 성공하셨습니다",Toast.LENGTH_SHORT).show()
                            Log.e("database", "DocumentSnapshot successfully delete!") }
                        .addOnFailureListener { e -> Log.e("database", "Error delete document") }
                }
                1->{
                    val docRef = db!!.collection("User").document(_id)
                    Log.e("fire Delete","docRef")

                    /*    delete()
                        .addOnSuccessListener {
                            Toast.makeText(activity,"즐겨찾기 삭제",Toast.LENGTH_SHORT).show()
                            Log.e("database", "DocumentSnapshot successfully delete!") }
                        .addOnFailureListener { e -> Log.e("database", "Error delete document") }*/
                }
            }
//        val newCount = String.format("%03d", count + 1)
        }
    }
}