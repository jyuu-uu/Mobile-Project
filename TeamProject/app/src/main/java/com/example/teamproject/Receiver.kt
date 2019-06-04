package com.example.teamproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startForegroundService
import android.support.v4.app.NotificationCompat.getExtras



class Receiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        val intent1 = Intent(p0, NewIntentService::class.java)
//        p0!!.startService(intent1)

        // intent로부터 전달받은 string
        val get_yout_string = p1!!.getExtras().getString("state")

        val a_id=p1!!.getExtras().getString("a_id")
        val todo=p1!!.getExtras().getString("todo")
        val what=p1!!.getExtras().getString("what")
        // RingtonePlayingService 서비스 intent 생성
        val service_intent = Intent(p0, NewIntentService::class.java)

        // RingtonePlayinService로 extra string값 보내기
        service_intent.putExtra("state", get_yout_string)
        service_intent.putExtra("a_id", a_id)
        service_intent.putExtra("todo", todo)
        service_intent.putExtra("what", what)
        // start the ringtone service

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            p0!!.startForegroundService(service_intent)
        } else {
            p0!!.startService(service_intent)
        }
    }
}