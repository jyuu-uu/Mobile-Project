package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        init()
    }

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
            }
            R.id.nav_list->{
                val intent = Intent(this,ListActivity::class.java)
                startActivity(intent)
            }
            R.id.bookmark -> {

            }
            //R.id.nav_manage -> {

            //}
            R.id.near_market -> {

            }
            R.id.embassy_phone -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private var tabLayer: TabLayout?= null
    var User:String?=null

    fun init(){

        User = intent.getStringExtra("User")
        // 사용자 아이디를 저장
        //-----------------
        tabLayer = findViewById(R.id.layout_tab)
        tabLayer?.addTab(tabLayer!!.newTab().setText("Tab 1"))
        tabLayer?.addTab(tabLayer!!.newTab().setText("Tab 2"))

        //탭 추가

        val adapter = TabAdapter(supportFragmentManager, tabLayer!!.tabCount)
        content.adapter = adapter

        content.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayer))

        tabLayer!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                  content.currentItem = tab.position}
        })

    }
}
