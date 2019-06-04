package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.fragment_country.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.util.*

class ReviewFragment: Fragment() {

    companion object{
        var autoSet = mutableListOf<String>()
    }

    var readfile = false
    var data:ArrayList<MyCafe> = ArrayList()
    lateinit var adapter:MyCafeAdapter
    lateinit var auto_adapter: ArrayAdapter<String>
    val list = arrayListOf("ababab","babca","cabse","dsefsw","esesw")

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
    fun init(){
        initLayout()
        initSwipe()
        if(readfile == false)   readFile()
        rcClick(review_listView)
        readApiFile()
    }
    fun rcClick(v:View){    //recycleview 에서 클릭한거 페이지 띄우기
    }

    fun initSwipe(){
        val simpleItemTouchCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
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

    fun initLayout(){
        val layoutManager = LinearLayoutManager(super.getContext(), LinearLayoutManager.VERTICAL, false)

        review_listView.layoutManager = layoutManager
        adapter = MyCafeAdapter(data)
        review_listView.adapter = adapter

//        save.setOnClickListener {
//            var point_country = -1;
//            when(country.text.toString()){
//                "태국"->{
//                    for(i in data){
//                        if(i.country == "태국")
//                            point_country = i.hashCode()
//                        Log.v("review", point_country.toString())
//                    }
//                }
//                "미국"->{
//                    for(i in data){
//                        if(i.country == "미국")
//                            point_country = i.hashCode()
//                        Log.v("review", point_country.toString())
//                    }
//                }
//                else->{
//                    Log.v("review", country.text.toString())
//                }
//            }
//            adapter.moveItem(0, point_country)
//        }
    }

    fun readFile(){ //words, array 초기화
        var scan = Scanner(resources.openRawResource(R.raw.test_text))
        while(scan.hasNextLine()){
            val str = scan.nextLine()
            val str_result = str.split(',')
            data.add(0, MyCafe(str_result[0], str_result[5], str_result[1]))
        }
        readfile = true
        scan.close()
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
        var factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true //네임 스페이스
        val xpp = factory.newPullParser()
        xpp.setInput(result, "utf-8") //한글 모드를 읽을 방법 설정
        var eventType = xpp.eventType

        var status = 0
        var i = 0 // 배열 정보를 찾을 반복문 인덱스

        var krName = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_DOCUMENT -> {
                }

                XmlPullParser.START_TAG -> {
                    val tagname = xpp.name
                    if (tagname.equals("body"))
                    // 내용을 불러와야 할 공간에 접근
                        status = 1
                    else if (tagname.equals("item"))
                        if (status == 1)
                            status = 2
                        else
                            status = 3
                    else if (tagname.equals("countryName")) //한글이름
                        status = 6

                }
                XmlPullParser.TEXT -> {
                    if (status > 3)
                        when (status) {
                            6 -> krName = xpp.text
                        }
                } // 하나의 객체 추가 끝
                XmlPullParser.END_TAG -> {
                    if (status == 8) {
                        // 마지막 정보까지 다 읽었으면
                        autoSet.add(krName)
                        status = 3
                    }
                }
            }
            eventType = xpp.next() // 다음으로 넘어감
        }
        settingView()
    }

    fun settingView() {

        autoSet.add("atest")

        val aAdapter = ArrayAdapter(activity!!.applicationContext,
            android.R.layout.simple_dropdown_item_1line, autoSet
        )
        r_auto.setAdapter(aAdapter) // 해당 위젯(객체)에 어뎁터 연결
        // 이후 autoCompleteTextView의 속성 중, completionThreshold 값을 지정해줘야 함
        r_auto.setOnItemClickListener { parent, view, position, id ->
             r_country.text = parent.getItemAtPosition(position).toString()
        }

        r_find.setOnClickListener {
//            val i = Intent(context,)
            Toast.makeText(context,r_country.text,Toast.LENGTH_SHORT).show()
        }

    }
}