package com.example.teamproject

import android.net.http.HttpResponseCache
import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread


class ApiExplorer {
    fun init() {
//        val urlBuilder = StringBuilder("http://apis.data.go.kr/1262000/CountryBasicService/getCountryBasicList") /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("http://apis.data.go.kr/1262000/CountryBasicService/getCountryBasicList?ServiceKey"
//            , "UTF-8") + "=YPUbhgq7ZfByj7es%2BhG98WHeCJN%2BsdnJ7tDgJR1J4bqLVBD5ovQqCV3x5sSESNwL63nii%2FvMx9Dah708x8xZDw%3D%3D") /*Service Key*/
//        urlBuilder.append(
//            "&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(
//                "YPUbhgq7ZfByj7es%2BhG98WHeCJN%2BsdnJ7tDgJR1J4bqLVBD5ovQqCV3x5sSESNwL63nii%2FvMx9Dah708x8xZDw%3D%3D",
//                "UTF-8"
//            )
//        ) /*공공데이터 포털에서 발급받은 인증키*/
//        urlBuilder.append(
//            "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(
//                "1",
//                "UTF-8"
//            )
//        ) /*한페이지의 결과*/
//        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")) /*페이지번호*/
//        urlBuilder.append(
//            "&" + URLEncoder.encode("countryName", "UTF-8") + "=" + URLEncoder.encode(
//                "가나",
//                "UTF-8"
//            )
//        ) /*한글국가명*/
//        urlBuilder.append(
//            "&" + URLEncoder.encode("countryEnName", "UTF-8") + "=" + URLEncoder.encode(
//                "Ghana",
//                "UTF-8"
//            )
//        ) /*영문국가명*/
//        urlBuilder.append(
//            "&" + URLEncoder.encode("isoCode1", "UTF-8") + "=" + URLEncoder.encode(
//                "211",
//                "UTF-8"
//            )
//        ) /*ISO국가코드*/
//        val url = URL(urlBuilder.toString())
//        val conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "GET"
//
//        conn.setRequestProperty("basic", "application/json")
//        var rd: BufferedReader? = null
//        thread {
//        if (conn.responseCode >= 200 && conn.responseCode <= 300) {
//            rd = BufferedReader(InputStreamReader(conn.inputStream) as Reader?)
//        } else {
//            rd = BufferedReader(InputStreamReader(conn.errorStream))
//        } }.run()
//        val sb = StringBuilder()
//        var line: String
//        for (line in rd!!.readLine()) {
//            sb.append(line)
//        }
//        rd!!.close()
//        conn.disconnect()
//        Log.v("viewprint",sb.toString())
    }
}