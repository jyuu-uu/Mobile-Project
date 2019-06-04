package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myreportaftertravel.MyCafe
import com.example.myreportaftertravel.MyCafeAdapter
import com.example.teamproject.ReviewFragment.Companion.autoSet
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.fragment_country.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.util.*

class ReviewFragment: Fragment() {

    companion object {
        var autoSet = mutableListOf<String>()
    }

    var data: ArrayList<MyCafe> = ArrayList()
    lateinit var adapter: MyCafeAdapter
    lateinit var auto_adapter: ArrayAdapter<String>
    val list = arrayListOf("ababab", "babca", "cabse", "dsefsw", "esesw")
    lateinit var db :Firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init() {
        db = Firestore.create(context!!)
        readDB()
        initLayout()
        initSwipe()
        rcClick(review_listView)
        if(autoSet.size== 0)
            readApiFile()
        else
            settingView()
    }

    fun rcClick(v: View) {    //recycleview 에서 클릭한거 페이지 띄우기
    }

    fun initSwipe() {
        val simpleItemTouchCallBack =
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    adapter.moveItem(p1.adapterPosition, p2.adapterPosition)
                    return true
                }

                override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    adapter.removeItem(p0.adapterPosition)
                }
            }   //객체 생성
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(review_listView)
    }

    fun initLayout() {
        val layoutManager = LinearLayoutManager(super.getContext(), LinearLayoutManager.VERTICAL, false)

        review_listView.layoutManager = layoutManager
        adapter = MyCafeAdapter(data)
        review_listView.adapter = adapter
    }

    fun readApiFile() {
        Ion.with(this).load(
            "http://apis.data.go.kr/1262000/ContactService/getContactList?" +
                    "ServiceKey=pL5pQCttuhZJvrgbr%2BY6eiEVd80nWpkcbuJGfXc5NbQyFe93iA5ZVwCAmIqqjZDIuyDmi4jO%2Bu%2Fk8eXd%2BX3Dzg%3D%3D" +
                    "&numOfRows=196"
        )
            .asInputStream() // inputStream 타입으로 데이터를 받아옴
            .setCallback { e, result ->
                e
                if (result != null) {
                    parsingXML(result) // 데이터를 잘 받아왔으면 파싱
                } else
                    Toast.makeText(activity!!.applicationContext, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    fun parsingXML(result: InputStream) {
        Log.e("오토 체크","파싱중..")
        var factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true //네임 스페이스
        val xpp = factory.newPullParser()
        xpp.setInput(result, "utf-8") //한글 모드를 읽을 방법 설정
        var eventType = xpp.eventType

        var check = 0
        var status = false
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {
                }
                XmlPullParser.START_TAG -> {
                    check++
                    val tagname = xpp.name
    //                Log.e("뭐가들었니","$tagname")
                    if (tagname.equals("countryName")) //한글이름
                        status = true
                }
                XmlPullParser.TEXT -> {
                    if(status){
                        autoSet.add(xpp.text)
                        status = false
                    }
                }
                XmlPullParser.END_TAG -> {}
            }
            eventType = xpp.next() // 다음으로 넘어감
        }
        Log.e("xml 갯수",check.toString())
        settingView()
    }

    fun settingView() {

        Log.e("오토 갯수",autoSet.size.toString())
        val aAdapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_dropdown_item_1line, autoSet
        )
        r_auto.setAdapter(aAdapter) // 해당 위젯(객체)에 어뎁터 연결
        // 이후 autoCompleteTextView의 속성 중, completionThreshold 값을 지정해줘야 함
        r_auto.setOnItemClickListener { parent, view, position, id ->
            r_country.text = parent.getItemAtPosition(position).toString()
        }

        r_find.setOnClickListener {
            val i = Intent(context,DReviewActivity::class.java)
            i.putExtra("find",r_country.text.toString())
            startActivity(i)
        }
    }

    fun readDB() {
        val isExist = db!!.db?.collection("Travel")?.get()
            ?.addOnCompleteListener { task ->
                Log.e("리뷰", "접속")
                for (k in task.result!!) {
                    Log.e("리뷰", "$k")
                    data.add(MyCafe(k.get("t_where").toString(),k.get("t_when").toString(),k.get("t_who").toString()))
                }
                adapter.notifyDataSetChanged()
            }
    }
}
