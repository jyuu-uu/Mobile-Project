package com.example.teamproject

import android.os.Message
import android.support.annotation.NonNull
import android.telecom.Call
import android.util.Log
import android.widget.Toast

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FireBaseManager {
    var user = HashMap<String, HashMap<String, Any>>()
    var meeting = HashMap<String, HashMap<String, Any>>()
    var myRoom = HashMap<String, HashMap<String, Any>>()
    var database: DatabaseReference? = null

    fun FireBaseManager() {
        database = FirebaseDatabase.getInstance().reference
        setMeetingInfo()
        setUserInfo()
        setMyMeetingRoom()
    }

    fun inputUserInfo( // 정보 받기
        id: String, password: String, name: String, nickname: String, birth: String, gender: Int,
        eatingCharacter: IntArray, character: BooleanArray) {

        val newuser = HashMap<String, Any>()
        newuser["Password"] = password
        newuser["name"] = name
        newuser["nickname"] = nickname
        newuser["birth"] = birth
        newuser["gender"] = gender

        for (i in 0..1)
            newuser["eating_character$i"] = eatingCharacter[i]


        for (i in 0..4)
            newuser["character$i"] = character[i]


        database?.child("UserInfo")?.child(id)?.setValue(newuser)

    }

    fun inputMeetingInfo(id: String, name: String, leaderId: String, period: Int,
        sido: String, sigugun: String, dongeupmyun: String, time: Int,
        foodkind: Int, minage: Int, maxage: Int, gender: Int,
        limit: Int, member: HashMap<String, Any>) { // 만남 정보
        val newmeeting = HashMap<String, Any>()
        newmeeting["name"] = name
        newmeeting["leaderId"] = leaderId
        newmeeting["period"] = period
        newmeeting["loca_sido"] = sido
        newmeeting["loca_sigugun"] = sigugun
        newmeeting["loca_dongeupmyun"] = dongeupmyun
        newmeeting["time"] = time
        newmeeting["foodkind"] = foodkind
        newmeeting["minage"] = minage
        newmeeting["maxage"] = maxage
        newmeeting["gender"] = gender
        newmeeting["limit"] = limit
        newmeeting["member"] = member

        //meeting.put(id,newmeeting);
        database?.child("MeetingInfo")?.child(id)?.setValue(newmeeting)
    }

    fun inputMyMeetingRoom(clientId: String, meetingId: String, whetherLeader: Boolean) {
        database?.child("MyMeetingRoom")?.child(clientId)?.child(meetingId)?.setValue(whetherLeader)
    }

    fun setUserInfo() {
        val mPostReference = database?.child("UserInfo")
        mPostReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //여기서 데이터를 받아오면 됨
//                user = dataSnapshot.value as HashMap<String, ???>?
                Log.e("FireBase", "FireBase_user")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e("FireBase",  "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })

    }

    fun setMeetingInfo() {
        val mPostReference = database?.child("MeetingInfo")
        mPostReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //여기서 데이터를 받아오면 됨
//                meeting = dataSnapshot.value as HashMap<String, ???>?
                Log.e("FireBase", "FireBase_meeting")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e("FireBase", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    fun setMyMeetingRoom() {
        val mPostReference = database?.child("MyMeetingRoom")
        mPostReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //여기서 데이터를 받아오면 됨
 //               myRoom = dataSnapshot.value as HashMap<String, ???>?
                Log.e("FireBase", "FireBase_mymeetingroom")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.e("FireBase",  "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
    // 실시간은 정보가 바뀔 때만 값으 읽어올 수 있음
}