package com.example.teamproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
import android.util.Log
import android.widget.Toast
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_cintent.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class CIntentActivity : AppCompatActivity() {

    var i = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent)

        init()
    }

    fun init(){
         i = intent.getIntExtra("data",-1)
        readFile()
    }

    fun readFile() {
        Ion.with(this).load(
            "http://apis.data.go.kr/1262000/ContactService/getContactList?" +
                    "ServiceKey=Oo7qsSTH9FGc46zjNGEuY4so7EBOkiYNknKqAZjFTcKL3Y904CmGwDctVZehoJYjl" +
                    "QrDDdzBPP3g%2FXLIWBDioA%3D%3D" +
                    "&numOfRows=1&pageNo="+(i+1).toString()
        )
            .asInputStream() // inputStream 타입으로 데이터를 받아옴
            .setCallback { e, result ->
                e
                if (result != null) {
                    parsingXML(result) // 데이터를 잘 받아왔으면 파싱
                } else
                    Toast.makeText(applicationContext, "데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
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

        var part = ""
        var enName = ""
        var krName = ""
        var countryId = ""
        var flag = ""
        var info = ""
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
                    else if(tagname.equals("contact"))
                        status = 9
                }
                XmlPullParser.TEXT -> {
                    if (status > 3)
                        when (status) {
                            4 -> part = xpp.text
                            5 -> enName = xpp.text
                            6 -> krName = xpp.text
                            7 -> countryId = xpp.text
                            8 -> flag = xpp.text
                            9 -> info = xpp.text
                        }
                } // 하나의 객체 추가 끝
                XmlPullParser.END_TAG -> {
                    if (status == 8) {
                        // 마지막 정보까지 다 읽었으면
                        c_content.text = Html.fromHtml(info,0)
                        status = 3
                    }
                }
            }
            eventType = xpp.next() // 다음으로 넘어감
        }
    }
}