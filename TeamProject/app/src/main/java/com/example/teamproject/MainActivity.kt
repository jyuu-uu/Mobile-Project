package com.example.teamproject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteQuery
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*

lateinit var sqlite :SQLite
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val REQUEST_PERMISSION = 100
    val permissionArray = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar!!.setIcon(R.drawable.title_logo)

        sqlite = SQLite(this,"Login")
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        makeMain()
        Loading()
        initPermission()
    }

    fun initPermission(){
        if(!checkAppPermission (permissionArray)){ //권한이 있는지 확인
            val builder = AlertDialog.Builder(this)
            builder.setMessage("반드시 파일 접근에 대한 권한이 허용되어야 합니다.")
                .setTitle("권한 허용")
                .setIcon(R.drawable.abc_ic_star_black_48dp)
            builder.setPositiveButton("OK") { _, _ ->
                askPermission (permissionArray, REQUEST_PERMISSION );
            }
            val dialog = builder.create()
            dialog.show() // 다이얼로그 생성 (권한 승인 요청창)
        }else{
            Toast.makeText ( getApplicationContext(),
                "권한이 승인되었습니다." , Toast . LENGTH_SHORT ). show ();
        }
    }

    fun checkAppPermission(requestPermission: Array<String>): Boolean {
        val requestResult = BooleanArray(requestPermission.size)
        Log.e("권한 검사","checkAppPermission")
        for (i in requestResult.indices) {
            requestResult[i] = ContextCompat.checkSelfPermission(this,
                requestPermission[i])== PackageManager.PERMISSION_GRANTED
            // 해당 권한을 갖고있는지 확인
            if (!requestResult[i]) { //권한이 없다면
                return false
            }
        }
        return true //그 외엔 OK
    } // checkAppPermission

    fun askPermission(requestPermission: Array<String>, REQ_PERMISSION: Int) {
        Log.e("권한 검사","askPermission")
        ActivityCompat.requestPermissions(this, requestPermission, REQ_PERMISSION)
        // 권한 승인 요청
    } // askPermission

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> if (checkAppPermission(permissions)) { //퍼미션 동의했을 때 할 일
                Toast.makeText(this, "권한이 승인됨", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "권한이 거절됨", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    } // onRequestPermissionsResult

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
        // 오른쪽옵션생성 창을 없애버림
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.my_info -> {
                makeMain()
            }
            R.id.nav_list->{
//                val intent = Intent(this,ListActivity::class.java)
//                startActivity(intent)
                makeList()
            }
            R.id.bookmark -> {
                    makeFav()
            }
            //R.id.nav_manage -> {

            //}
            R.id.near_market -> {
                makeMap()
            }
            R.id.embassy_phone -> {
                makeCountry()
            }
            R.id.logout->{
                sqlite?.dropDB()
                makeMain()
                val intent = Intent(this, login::class.java)
                startActivityForResult(intent, code)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    var User:String?=null
    val code = 100
    var firestore :Firestore? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 로그인창으로 부터 정보전달
        if (requestCode == code) {
            if (resultCode == Activity.RESULT_OK) { // 결과로 보내준 상태가 OK 코드면
                User = data?.getStringExtra("id") //값을 받아옴
                Log.e("Main","$User")
                sqlite = SQLite(this,"Schedule")
                // 만들어둔 테이블 정보

                firestore = Firestore.create(applicationContext)
            }
            else{
                finish() //받은 정보가 없으면 로그인 실패
                // 종료
            }
        }
    }

    fun Loading(){
        val check = intent.getBooleanExtra("success", false)
        if(check != null && !check){ //실패했다면
            val intent = Intent(this, login::class.java)
            startActivityForResult(intent, code)
        }
        else {
            User = intent.getStringExtra("id") // 아이디 저장
        }
    }

    // 메인 만들기
    fun makeMain(){
        // 메인 프레그먼트를 만드는 함수

        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
            // 해당 view에 fragment가 부착되어 있으면 해당 fragment를 반환
        if(fragment == null) {  // 부착된게 없으면
            val imageTransaction = supportFragmentManager.beginTransaction()
                // 서포트프레그먼트매니저를 통해 프레임 생성/교체하는 작업을 시작

            val imageFrag = MainFragment.newFragment() // 프레그먼트 객체 생성
            imageTransaction.replace(R.id.frame,imageFrag,"mainFrag")
                // 이미지 교체 작업까지 완료
// 만들어진 프레그먼트에 대해서 태그를 붙여서 넘김
// cause, 동적 방식에선 id 값이 지정되지 않기 때문

            imageTransaction.commit() // 실제 수행
            //m_imageFrag = true // 이미지 프레그먼트 부착 완료
        }
        else{ // 부착된 프레그먼트가 이미 존재
            val imageFragment = supportFragmentManager.findFragmentByTag("mainFrag")
                // 이미지 프레그먼트의 tag값 을 통해 프레그먼트 획득 시도
            if(imageFragment == null){
                // 부착된 프레그먼트가 메인 프레그먼트가 아닌 경우

                val imageTransaction = supportFragmentManager.beginTransaction()
                // 서포트프레그먼트매니저를 통해 프레임 생성/교체하는 작업을 시작

                val imageFrag = MainFragment.newFragment()
                    // 2. fragment에 해당하는 객체를 마음대로 생성하지 않고
                    // 생성해주는 함수를 만들어놓고, 해당 함수를 통해 객체를 '받는' 방식이 가능
                    // 더 일반적인 방법이며, 해당 방법을 위해선 자바의 static 기능이 필요
                    // so, 생성할 fragment 내부에 companion object 를 통한 함수 선언
                Log.e("순서","$imageFrag")
                imageTransaction.replace(R.id.frame,imageFrag,"mainFrag")
                // 이미지 교체 작업까지 완료
                imageTransaction.commit()
            }
            else{ // 동일한 프레그먼트
                // 무시
            }
        }
    }

    fun makeFav() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        // 해당 view에 fragment가 부착되어 있으면 해당 fragment를 반환

        val favFragment = supportFragmentManager.findFragmentByTag("favFrag")
        // 이미지 프레그먼트의 tag값 을 통해 프레그먼트 획득 시도
        if (favFragment == null) {
            // 부착된 프레그먼트가 메인 프레그먼트가 아닌 경우

            val favTransaction = supportFragmentManager.beginTransaction()
            // 서포트프레그먼트매니저를 통해 프레임 생성/교체하는 작업을 시작

            val favFrag = FavFragment.create(User!!)

            Log.e("순서","$favFrag")
            // 2. fragment에 해당하는 객체를 마음대로 생성하지 않고
            // 생성해주는 함수를 만들어놓고, 해당 함수를 통해 객체를 '받는' 방식이 가능
            // 더 일반적인 방법이며, 해당 방법을 위해선 자바의 static 기능이 필요
            // so, 생성할 fragment 내부에 companion object 를 통한 함수 선언

            favTransaction.replace(R.id.frame, favFrag, "favFrag")
            // 이미지 교체 작업까지 완료
            favTransaction.commit() // 실제 수행
            //              m_imageFrag = true // 이미지 프레그먼트 부착 완료
        } else { // 동일한 프레그먼트
            // 무시
        }
    }

    fun makeMap() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        // 해당 view에 fragment가 부착되어 있으면 해당 fragment를 반환

        val mapFragment = supportFragmentManager.findFragmentByTag("mapFrag")
        // 이미지 프레그먼트의 tag값 을 통해 프레그먼트 획득 시도
        if (mapFragment == null) {
            // 부착된 프레그먼트가 메인 프레그먼트가 아닌 경우

            val mapTransaction = supportFragmentManager.beginTransaction()
            // 서포트프레그먼트매니저를 통해 프레임 생성/교체하는 작업을 시작

            val mapFrag = MapsActivity()
            // 2. fragment에 해당하는 객체를 마음대로 생성하지 않고
            // 생성해주는 함수를 만들어놓고, 해당 함수를 통해 객체를 '받는' 방식이 가능
            // 더 일반적인 방법이며, 해당 방법을 위해선 자바의 static 기능이 필요
            // so, 생성할 fragment 내부에 companion object 를 통한 함수 선언

            mapTransaction.replace(R.id.frame, mapFrag, "mapFrag")
            // 이미지 교체 작업까지 완료
            mapTransaction.commit() // 실제 수행
            //              m_imageFrag = true // 이미지 프레그먼트 부착 완료
        } else { // 동일한 프레그먼트
            // 무시
        }
    }

    fun makeList(){
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        // 해당 view에 fragment가 부착되어 있으면 해당 fragment를 반환

        val listFragment = supportFragmentManager.findFragmentByTag("listFrag")
        // 이미지 프레그먼트의 tag값 을 통해 프레그먼트 획득 시도
        if (listFragment == null) {
            // 부착된 프레그먼트가 메인 프레그먼트가 아닌 경우

            val listTransaction = supportFragmentManager.beginTransaction()
            // 서포트프레그먼트매니저를 통해 프레임 생성/교체하는 작업을 시작

            val listFrag = ListFragment.create(User!!)

            // 2. fragment에 해당하는 객체를 마음대로 생성하지 않고
            // 생성해주는 함수를 만들어놓고, 해당 함수를 통해 객체를 '받는' 방식이 가능
            // 더 일반적인 방법이며, 해당 방법을 위해선 자바의 static 기능이 필요
            // so, 생성할 fragment 내부에 companion object 를 통한 함수 선언

            listTransaction.replace(R.id.frame, listFrag, "listFrag")
            // 이미지 교체 작업까지 완료
            listTransaction.commit() // 실제 수행

        } else { // 동일한 프레그먼트
            // 무시
        }
    }

    fun makeCountry(){
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        // 해당 view에 fragment가 부착되어 있으면 해당 fragment를 반환

        val cFragment = supportFragmentManager.findFragmentByTag("cFrag")
        // 이미지 프레그먼트의 tag값 을 통해 프레그먼트 획득 시도
        if (cFragment == null) {
            // 부착된 프레그먼트가 메인 프레그먼트가 아닌 경우

            val cTransaction = supportFragmentManager.beginTransaction()
            // 서포트프레그먼트매니저를 통해 프레임 생성/교체하는 작업을 시작

            val cFrag = CountryFragment()

            // 2. fragment에 해당하는 객체를 마음대로 생성하지 않고
            // 생성해주는 함수를 만들어놓고, 해당 함수를 통해 객체를 '받는' 방식이 가능
            // 더 일반적인 방법이며, 해당 방법을 위해선 자바의 static 기능이 필요
            // so, 생성할 fragment 내부에 companion object 를 통한 함수 선언

            cTransaction.replace(R.id.frame, cFrag, "cFrag")
            // 이미지 교체 작업까지 완료
            cTransaction.commit() // 실제 수행
        } else { // 동일한 프레그먼트
            // 무시
        }
    }
}
