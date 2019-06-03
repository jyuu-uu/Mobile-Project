package com.example.teamproject


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.sql.Date
import java.sql.Time

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
                        "create table if not exists $tableName(a_id integer autoincrement, date date, time time, schedule text, what text)"
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
    fun insertData(date:Date, time:Time, schedule:String, what:String) {    //alarm
        Log.e("SQLite","insertData() 호출됨.")
        Log.e("SQLite","$date\t$time\t$schedule\t$what.")
        if (database != null) {

            val sql = "insert into " +
                    /*삽입할 테이블 이름*/ "Alarm" + "(date, time, schedule, what) values(?, ?, ?, ?)"
            val params = arrayOf(date, time, schedule, what)
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

    fun deleteData(w_id:Int){
        Log.e("SQLite","deleteData() 호출됨.")
        if (database != null) {
            val sql = "delete from "+ tableName+" where w_id="+w_id.toString() //조건 기재
            val cursor = database!!.rawQuery(sql, null) //파라미터는 없으니깐 null 값 넣어주면된다.
            Log.e("SQLite","delete 성공.")
        }
    }

    fun dropDB(){
        Log.e("SQLite","dropDB() 호출됨.")

        v.deleteDatabase("USER.db")
    }

    internal inner class DatabaseHelper(
        context: Context,
        name: String,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase) {
            //데이터베이스를 처음 생성해주는 경우(기존에 사용자가 데이터베이스를 사용하지 않았던 경우)
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
}