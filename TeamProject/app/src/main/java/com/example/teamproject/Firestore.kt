package com.example.teamproject

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Firestore {

    var db: FirebaseFirestore? = null
    companion object{
        fun create():Firestore{
            var database = Firestore()
            database.db = FirebaseFirestore.getInstance()

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
                .addOnSuccessListener { Log.e("database", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.e("database", "Error writing document") }
        }
    }


}