package com.example.teamproject


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.common.net.HttpHeaders.FROM
import java.sql.Date
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

class SQLite(val v:Context, val tableName: String) {
    internal var database: SQLiteDatabase? = null
    lateinit var databaseName:String


//            openDatabase(databaseName)
//            createTable(tableName)
//
//            val name = edit3.text.toString().trim { it <= ' ' }//넣을 데이터 값 설정, trim을 해줌으로 공백으로 인한 에러방지
//            val ageStr = edit4.text.toString().trim { it <= ' ' }//넣을 데이터 값 설정
//            val mobile = edit5.text.toString().trim { it <= ' ' }//넣을 데이터 값 설정
//            val age = -1
//            try {
//                Integer.parseInt(ageStr)
//            } catch (e: Exception) {
//            }
//
//            insertData(name, age, mobile)
//            selectData(tableName)

    fun openDatabase(databaseName: String) {
        Log.e("SQLite","openDatabase() 호출됨")
        //database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null); //보안때문에 요즘은 대부분 PRIVATE사용,
        //SQLiteDatabase객체가 반환됨
        if (database != null) {
            println("데이터베이스 오픈됨");
        }
        val helper = DatabaseHelper(v, databaseName+".db", null, 3) //헬퍼를 생성함
//         DatabaseHelper helper = new DatabaseHelper(this , databaseName, null, 4);
        // 위에거 실행후 이거 실행했을 경우 (이미 해당 디비가있으므로 헬퍼의 update가 호출될것이다.)

        database = helper.writableDatabase  //데이터베이스에 쓸수 있는 권한을 리턴해줌(갖게됨)

        createTable("Login")
        createTable("Schedule")
        createTable("Alarm")
    }

    fun AutoLogin():ArrayList<String>?{
        if (database != null) { // 데이터베이스가 존재하지 않으면

//            Log.e("SQLite","$tableName 으로 자동로그인 시도")
//            val c = database?.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'", null)
//            Log.e("SQLite","$c")
//            if(c. == null)
//                createTable(tableName) // 테이블이없으면 생성

            val sql = "select * from Login" //조건 기재
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","조회된 데이터개수 :" + cursor.count)

            if(cursor?.count == 1){
                // 정보가 1개 이상 들어있단 이야기.
                // 자동로그인이 되어어야함
                cursor.moveToNext()
                return arrayListOf(cursor.getString(0),cursor.getString(1))
                // 정보를 반환
            }
            else{  // 정보 없음. 로그인(회원가입) 필요
                return null //null 반환
            }
            cursor.close() //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다.
            // 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
        }
        return null // 생성 필요. null 반환
    }

    fun createTable(tableName: String) {
        Log.e("SQLite","createTable() 호출됨.")

        if (database != null) {
            when(tableName) {
                "Login"->{
                    val sql =
                        "create table if not exists $tableName(id text, pw text)"
                    // 테이블 만들기 이름(속성들)
                    database!!.execSQL(sql)
                }
                "Schedule"->{
                    val sql =
                        "create table if not exists $tableName(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)"
                    // 테이블 만들기 이름(속성들)
                    database!!.execSQL(sql)
                }
                "Alarm"->{
                    val sql =
                        "create table if not exists $tableName(a_id integer PRIMARY KEY autoincrement, date text, time text, schedule text, what text, sno integer)"
                    database!!.execSQL(sql)
                }

            }
            Log.e("SQLite","$tableName 테이블 생성됨.")
        } else {
            Log.e("SQLite","데이터베이스를 먼저 오픈하십쇼")
        }
    }

    fun insertData(i_id: String, i_pw: String) {
        Log.e("SQLite","insertData() 호출됨.")
        Log.e("SQLite","$i_id\t$i_pw.")
        if (database != null) {

            val sql = "insert into " +
                    /*삽입할 테이블 이름*/ "Login" + "(id, pw) values(?, ?)"
            val params = arrayOf(i_id, i_pw)
            database!!.execSQL(sql, params)
            //이런식으로 두번쨰 파라미터로 이런식으로 객체를 전달하면 sql문의 ?를 이 params에 있는 데이터를 물음표를 대체해준다.
            Log.e("SQLite", "데이터 추가함")

        } else {
            Log.e("SQLite", "데이터베이스를 먼저 오픈하시오")
        }
    }

    fun insertData(table:String, name: String, age: Int, mobile: String) {
        Log.e("SQLite","insertData() 호출됨.")

        if (database != null) {

//            val c = database?.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'", null)
//
//            if(c == null)
//                createTable(tableName) // 테이블이없으면 생성

            val sql = "insert into " +
                    /*삽입할 테이블 이름*/ "Schedule" + "(name, age, mobile) values(?, ?, ?)"
            val params = arrayOf(name, age, mobile)
            database!!.execSQL(sql, params)
            //이런식으로 두번쨰 파라미터로 이런식으로 객체를 전달하면 sql문의 ?를 이 params에 있는 데이터를 물음표를 대체해준다.
            Log.e("SQLite", "데이터 추가함")


        } else {
            Log.e("SQLite", "데이터베이스를 먼저 오픈하시오")
        }
    }

    fun selectData(tableName: String) {
        Log.e("SQLite","selectData() 호출됨.")
        if (database != null) {
            val sql = "select name, age, mobile from $tableName" //조건 기재
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","조회된 데이터개수 :" + cursor.count)

            //for문으로해도되고 while 문으로 해도됨.
            for (i in 0 until cursor.count) {
                cursor.moveToNext()//이걸 해줘야 다음 레코드로 넘어가게된다.
                val name = cursor.getString(0) //첫번쨰 칼럼을 뽑아줌
                val age = cursor.getInt(1)
                val mobile = cursor.getString(2)

                // 데이터 출력
            }
            cursor.close() //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다. 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
        }
    }
    fun selectData(context: Context, tableName: String) {
        Log.e("SQLite","selectData() 호출됨.")
        if (database != null) {
            val sql = "select date, time, schedule, what from $tableName" //조건 기재
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","조회된 데이터개수 :" + cursor.count)

            //for문으로해도되고 while 문으로 해도됨.
            for (i in 0 until cursor.count) {
                cursor.moveToNext()//이걸 해줘야 다음 레코드로 넘어가게된다.
                val date = cursor.getString(0) //첫번쨰 칼럼을 뽑아줌
                val time = cursor.getString(1)
                val schedule = cursor.getString(2)
                val what = cursor.getString(3)
                Log.e("alarm data",date+time+schedule+what)
                // 데이터 출력
            }
            cursor.close() //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다. 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
        }
    }

    fun deleteData(w_id:Int){
        Log.e("SQLite","deleteData() 호출됨.")
        if (database != null) {
            val sql = "delete from "+ tableName+" where w_id="+w_id.toString() //조건 기재
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","delete 성공.")
        }
    }
//    fun deleteData(what: String, tableName: String){
//        Log.e("SQLite","deleteData() 호출됨.")
//        if (database != null) {
//            val sql = "delete from "+ tableName+" where what="+what.toString() //조건 기재
//            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
//            Log.e("SQLite","delete 성공.")
//        }
//    }

    fun dropDB(){
        Log.e("SQLite","dropDB() 호출됨.")

        v.deleteDatabase("USER.db")
    }

    fun dropTable(TABLE_NAME:String){
        var sql = "drop table " + TABLE_NAME+";"

            database!!.execSQL(sql);
            Log.e("database test", "Table delete");

    }
    internal inner class DatabaseHelper(
        context: Context,
        name: String,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase) {
            //데이터베이스를 처음 생성해 주는 경우(기존에 사용자가 데이터베이스를 사용하지 않았던 경우)
            Log.e("SQLite","createCreate() 호출됨.")
            val tableName = "USER"
            //이 함수에서 데이터베이스는 매개변수인 db를 써야한다.
            //테이블 생성할떄 if not exists라는 조건문을 넣어줄 수 있다. (존재하지않을때 테이블 생성)
            //_id는 SQLite에서 내부적으로 관리되는 내부 id이다

            val sql =
                "create table if not exists $tableName(_id integer PRIMARY KEY autoincrement, pw text)"
            db.execSQL(sql)

            Log.e("SQLite","테이블 생성됨.")

        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) { //기존 사용자가 디비를 사용하고있어서 그걸 업데이트(수정)해주는경우
            Log.e("SQLite","onUpgrade() 호출됨: $oldVersion, $newVersion")

            //여기 예제에서는 그냥 삭제했지만 보통의 Alter로 수정을 한다거나 할 수도 있다.
            if (newVersion > 1) {
                val tableName = "USER"
                db.execSQL("drop table if exists $tableName")
                Log.e("SQLite","테이블 삭제함")

                val sql =
                    "create table if not exists $tableName(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)"
                db.execSQL(sql)

                Log.e("SQLite","테이블 생성됨.")
            }
        }
    }
    fun deleterow(a_id:Int, tableName: String){         //alarm
        if (database != null) {
            val sql = "delete from "+ tableName+" where a_id="+a_id.toString() //조건 기재
            database!!.execSQL(sql) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","delete 성공.")
        }
    }
    fun deleterow(schedule:String, tableName: String){         //alarm
        if (database != null) {
            val sql = "delete from "+ tableName+" where schedule=\""+schedule+"\"" //조건 기재
            database!!.execSQL(sql) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","delete 성공.")
        }
    }
    fun insertData(date:String, time:String, schedule:String, what:String, sno:String) {    //alarm
        Log.e("SQLite","insertData() 호출됨.")
        Log.e("SQLite","$date\t$time\t$schedule\t$what\t$sno.")
        //Log.e("SQLite","$date\t$schedule\t$what.")
        if (database != null) {

            val sql = "insert into " +
                    /*삽입할 테이블 이름*/ "Alarm" + "(date, time, schedule, what, sno) values(?, ?, ?, ?, ?)"
            ///*삽입할 테이블 이름*/ "Alarm" + "(date, schedule, what) values(?, ?, ?)"
            val params = arrayOf(date, time, schedule, what, sno.toInt())
            database!!.execSQL(sql, params)
            //이런식으로 두번쨰 파라미터로 이런식으로 객체를 전달하면 sql문의 ?를 이 params에 있는 데이터를 물음표를 대체해준다.
            Log.e("SQLite", "데이터 추가함")

        } else {
            Log.e("SQLite", "데이터베이스를 먼저 오픈하시오")
        }
    }
    fun readAlarm():ArrayList<String>?{
        if (database != null) {
            val sql =  "select * from Alarm order by date,time"
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite2","조회된 데이터개수 :" + cursor.count)
            if(cursor?.count != 0){
                // 정보가 1개 이상 들어있단 이야기.
                cursor.moveToNext()
                Log.e("array",cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4))
                return arrayListOf(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4))
                // 정보를 반환
            }
            else{  // 정보 없음. 로그인(회원가입) 필요
                return null //null 반환
            }
            cursor.close() //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다.
            // 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
        }
        return null // 생성 필요. null 반환
    }

    lateinit var my_intent : Intent
    lateinit var pendingIntent: PendingIntent
    lateinit var alarm_manager: AlarmManager

    fun readAlarm(context: Context){
        my_intent=Intent(context, Receiver::class.java)
        alarm_manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (database != null) {
            val sql =  "select * from Alarm order by date,time"
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite2","조회된 데이터개수 :" + cursor.count)
            var number=cursor.count
            //while(cursor.count!=0){
            while(number > 0){
                // 정보가 1개 이상 들어있단 이야기.
                cursor.moveToNext()
                Log.e("array",cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4))
                var arrayList = arrayListOf(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5))
                var id=arrayList[0]
                var date=arrayList[1]
                var time=arrayList[2]
                var todo=arrayList[3]
                var what=arrayList[4]
                var sno=arrayList[5]
                my_intent.putExtra("state","alarm on");
                my_intent.putExtra("a_id",id)
                my_intent.putExtra("todo",todo)
                my_intent.putExtra("what",what)
                my_intent.putExtra("sno",sno)

                pendingIntent = PendingIntent.getBroadcast(context, id.toInt(), my_intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

                var separate1 = date.split("-") //년-월-일 배열
                var separate2 = time.split(":") //시-분 배열

                val calendar = Calendar.getInstance()
                //알람시간 calendar에 set해주기
                //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 20, 27, 0)
                calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), separate2[0].toInt(), separate2[1].toInt(), 0)
                //calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), 16, 13, 0)
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
                number--
            }

            cursor.close() //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다.
            // 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
        }

    }

    fun searchAlarm(schedule: String, tableName: String):Int{
        if (database != null) {
            val sql =  "select * from Alarm order by date,time"
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite2","조회된 데이터개수 :" + cursor.count)
            var number=cursor.count
            //while(cursor.count!=0){
            while(number > 0){
                // 정보가 1개 이상 들어있단 이야기.
                cursor.moveToNext()
                var arrayList = arrayListOf(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4))
                var id=arrayList[0].toInt()
                var date=arrayList[1]
                var time=arrayList[2]
                var todo=arrayList[3]
                var what=arrayList[4]

                if(todo==schedule){
                    return id
                }
                number--
            }

            cursor.close() //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다.


        }
        return -1

    }
    fun alarm(context: Context){
        my_intent=Intent(context, Receiver::class.java)
        alarm_manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (database != null) {
            var readalarm= readAlarm()
            if(readalarm==null){
                return
            }
            //dropTable("Alarm")
            var id=readalarm[0]
            var date=readalarm[1]
            var time=readalarm[2]
            var todo=readalarm[3]
            var what=readalarm[4]
            my_intent.putExtra("state","alarm on");
            my_intent.putExtra("a_id",id)
            my_intent.putExtra("todo",todo)
            my_intent.putExtra("what",what)
            pendingIntent = PendingIntent.getBroadcast(context, 0, my_intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            Log.e("id",id)

            var separate1 = date.split("-") //년-월-일 배열
            var separate2 = time.split(":") //시-분 배열

            val calendar = Calendar.getInstance()
            //알람시간 calendar에 set해주기
            //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 20, 27, 0)
            calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), separate2[0].toInt(), separate2[1].toInt(), 0)
            //calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), 16, 13, 0)
            alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)


        }
//        val sqlite = SQLite(context,"Alarm")
//        sqlite.openDatabase("USER")
//        my_intent.putExtra("state","alarm on");
//
//        var readalarm= sqlite.readAlarm()
//        if(readalarm==null){
//            return
//        }
//        var id=readalarm[0]
//        var date=readalarm[1]
//        var time=readalarm[2]
//        var todo=readalarm[3]
//        var what=readalarm[4]
//        my_intent.putExtra("a_id",id)
//        my_intent.putExtra("todo",todo)
//        my_intent.putExtra("what",what)
//        pendingIntent = PendingIntent.getBroadcast(context, 0, my_intent,
//            PendingIntent.FLAG_UPDATE_CURRENT)
//        Log.e("id",id)
//
//        var separate1 = date.split("-") //년-월-일 배열
//        var separate2 = time.split(":") //시-분 배열
//
//        val calendar = Calendar.getInstance()
//        //알람시간 calendar에 set해주기
//        //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 20, 27, 0)
//        calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), separate2[0].toInt(), separate2[1].toInt(), 0)
//        //calendar.set(separate1[0].toInt(), separate1[1].toInt(), separate1[2].toInt(), 16, 13, 0)
//        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
//
//        //sqlite.dropTable("Alarm")
////        sqlite.deleterow(id.toInt(),"Alarm")
////        alarm()
//        //Log.e("alarm delete",id+" alarm deleted")
    }
}