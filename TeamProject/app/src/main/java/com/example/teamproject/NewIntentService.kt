package com.example.teamproject

import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.R
import android.app.*
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.content.Context
import android.os.IBinder
import android.media.MediaPlayer
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.support.v4.app.NotificationCompat
import io.grpc.internal.SharedResourceHolder.release
import android.support.v4.app.NotificationCompat.getExtras
import android.app.PendingIntent
import android.util.Log


class NewIntentService : Service() {
    //var mediaPlayer: MediaPlayer? = null
    var startId: Int = 0
    var isRunning: Boolean = false

    override fun onBind(p0: Intent?): IBinder? {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null;
    }

    override fun onCreate() {

        super.onCreate()
        val intent1 = Intent(this, MainActivity::class.java) //인텐트 생성.
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP);

        var a_id:String
        var todo:String
        var what:String

        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "default"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
            val pendingNotificationIntent =
                PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("푸시 제목 (일정)")
                .setContentText("푸시 내용 (준비물)")
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true)
                .build()

            startForeground(1, notification)

//            val sqlite = SQLite(this,"Alarm")
//            sqlite.openDatabase("USER")
//            sqlite.deleterow(id.toInt(),"Alarm")
//            alarm()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startid: Int): Int {
        Log.e("service","onstartcommand")

        val getState = intent!!.getExtras().getString("state")!!

        when (getState) {
            "alarm on" -> startId = 1
            "alarm off" -> startId = 0
            else -> startId = 0
        }

        // 알람음 재생 X , 알람음 시작 클릭
        if (!this.isRunning && startId === 1) {

            this.isRunning = true
            this.startId = 0
        } else if (this.isRunning && startId === 0) {
            this.isRunning = false
            this.startId = 0
        } else if (!this.isRunning && startId === 0) {

            this.isRunning = false
            this.startId = 0

        } else if (this.isRunning && startId === 1) {

            this.isRunning = true
            this.startId = 1
        } else {
        }// 알람음 재생 O , 알람음 시작 버튼 클릭
        // 알람음 재생 X , 알람음 종료 버튼 클릭
        // 알람음 재생 O , 알람음 종료 버튼 클릭
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //    private val NOTIFICATION_ID = 3
//    var INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
//
//
//    override fun onHandleIntent(p0: Intent?) {
////        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////        val builder = Notification.Builder(this)
////        builder.setContentTitle("My Title")
////        builder.setContentText("This is the Body")
////        builder.setSmallIcon(R.drawable.sym_def_app_icon)
////        val notifyIntent = Intent(this, MainActivity::class.java)
////        val pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
////        //to be able to launch your activity from the notification
////        builder.setContentIntent(pendingIntent)
////        val notificationCompat = builder.build()
////        val managerCompat = NotificationManagerCompat.from(this)
////        managerCompat.notify(NOTIFICATION_ID, notificationCompat)
//        val notificationmanager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            Intent(this, MainActivity::class.java),
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val builder = Notification.Builder(this)
//        builder.setSmallIcon(R.drawable.ic_dialog_alert).setTicker("HETT").setWhen(System.currentTimeMillis())
//            .setNumber(1).setContentTitle("푸쉬 제목").setContentText("푸쉬내용")
//            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        notificationmanager.notify(1, builder.build())
//
//    }

}