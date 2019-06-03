package com.example.teamproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.fragment_country.view.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class CountryFragment : Fragment() {

    lateinit var v : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_country, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        settingAdapter() // 어댑터 세팅
        readFile() // 이후 데이터 불러오기가 끝나면 갱신 리스너
    }

    fun readFile() {
        Ion.with(this).load(
            "http://apis.data.go.kr/1262000/ContactService/getContactList?" +
                    "ServiceKey=Oo7qsSTH9FGc46zjNGEuY4so7EBOkiYNknKqAZjFTcKL3Y904CmGwDctVZehoJYjl" +
                    "QrDDdzBPP3g%2FXLIWBDioA%3D%3D" +
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

    var dataSet = mutableListOf<CountryData>()
    var autoSet = mutableListOf<String>()
    var changeSet = mutableMapOf<String,Int>()

    fun parsingXML(result: InputStream) {
        var factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true //네임 스페이스
        val xpp = factory.newPullParser()
        xpp.setInput(result, "utf-8") //한글 모드를 읽을 방법 설정
        var eventType = xpp.eventType

        var status = 0
        var i = 0 // 배열 정보를 찾을 반복문 인덱스

        var part = ""
        var enName = ""
        var krName = ""
        var countryId = ""
        var flag = ""

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
                    else if (tagname.equals("continent")) // 지역
                        status = 4
                    else if (tagname.equals("countryEnName")) //영어이름
                        status = 5
                    else if (tagname.equals("countryName")) //한글이름
                        status = 6
                    else if (tagname.equals("id")) //고유번호
                        status = 7
                    else if (tagname.equals("imgUrl")) // 국기사진
                        status = 8
                }
                XmlPullParser.TEXT -> {
                    if (status > 3)
                        when (status) {
                            4 -> part = xpp.text
                            5 -> enName = xpp.text
                            6 -> krName = xpp.text
                            7 -> countryId = xpp.text
                            8 -> flag = xpp.text
                        }
                } // 하나의 객체 추가 끝
                XmlPullParser.END_TAG -> {
                    if (status == 8) {
                        // 마지막 정보까지 다 읽었으면
                        changeSet[krName]=dataSet.size
                        dataSet.add(CountryData(part, enName, krName, countryId, flag))
                        autoSet.add(krName)
                        status = 3
                    }
                }
            }
            eventType = xpp.next() // 다음으로 넘어감
        }
        adapter!!.notifyDataSetChanged() //데이터 갱신 완료
        val a = dataSet.size
        Log.e("country","데이터 갯수 = $a")
    }

    var adapter: CountryAdapter? = null
    fun settingAdapter() {
        adapter = CountryAdapter(activity!!.applicationContext, R.layout.country_form, dataSet)
        // keyset을 리스트에 뿌릴 어댑터
        v.country_list.setOnItemClickListener { parent, view, position, id ->
            // 리스트 뷰의 아이템을 클릭하면,
            // 해당하는 뉴스를 간략히 보여줄 액티비티로 화면전환
//            val intent = Intent( applicationContext, DetailActivity::class.java)
//            intent.putExtra( "news" , news.get( keySet.get( position )))
//            startActivity(intent)
//
            val i = Intent(activity!!.applicationContext,CIntentActivity::class.java)
            i.putExtra("data",position)
            startActivity(i)
//            Toast.makeText(activity!!.applicationContext, "$a", Toast.LENGTH_SHORT).show()
        }
        if (adapter != null) // 어댑터가 만들어졌으면 리스트뷰에 붙임
            v.country_list.adapter = adapter

        val aAdapter = ArrayAdapter(activity!!.applicationContext,
            android.R.layout.simple_dropdown_item_1line,autoSet)

        country_auto.setAdapter(aAdapter) // 해당 위젯(객체)에 어뎁터 연결
        // 이후 autoCompleteTextView의 속성 중, completionThreshold 값을 지정해줘야 함

        country_auto.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(activity!!.applicationContext,CIntentActivity::class.java)
            i.putExtra("data",changeSet[parent.getItemAtPosition(position)])
            startActivity(i)
        }

    }
}
