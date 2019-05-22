package com.example.teamproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    lateinit var auto_adapter: ArrayAdapter<String>
    val list = arrayListOf("ababab","babca","cabse","dsefsw","esesw")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        make()
    }
    fun make(){

        auto_adapter = ArrayAdapter(activity, android.R.layout.simple_dropdown_item_1line,list)
        // 어뎁터 사용 전 초기화 (대상 객체, 사용할 리스트방식, 연동할 데이터)

        search.setAdapter(auto_adapter) // 해당 위젯(객체)에 어뎁터 연결
        // 이후 autoCompleteTextView의 속성 중, completionThreshold 값을 지정해줘야 함

        search.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            // 부모로부터 선택된 위치의 내용을 받아와 문자열로 변환
       //     Toast.makeText(activity, "선택 항목 : $item", Toast.LENGTH_LONG)
        }
    }
}
