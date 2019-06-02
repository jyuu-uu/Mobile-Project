package com.example.teamproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.koushikdutta.ion.Ion

class CountryAdapter(context: Context, val resource:Int, var list:MutableList<CountryData>)
    : ArrayAdapter<CountryData>(context,resource,list) {
    // ctrl + O = 오버라이딩 가능한 함수 목록
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v:View? = convertView
        // 이미 만들어진 뷰가 있는 경우, 기존 뷰의 정보가 들어오고, 정보 수정
        // 처음인 경우, null 값이 들어옴 (view를 생성해줘야 함)
        if(v== null){ //만들어진 view가 없다면
            val vi = context.applicationContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            // layout 객체를 받아온 것과 동일
            v = vi.inflate(resource, null)
            // 레이아웃 정보와 루트로 사용 여부
        }

        val p = list.get(position)
        v!!.findViewById<TextView>(R.id.c_part).text = p.part
        v!!.findViewById<TextView>(R.id.c_enName).text = p.enName
        v!!.findViewById<TextView>(R.id.c_krName).text = p.krName

        Ion.with(v!!.findViewById<ImageView>(R.id.c_flag)).load(p.flag)
//        val a = Ion.with(context).load(p.flag).asBitmap().setCallback { e, result ->
//            v!!.findViewById<ImageView>(R.id.c_flag).setImageBitmap(result)
//        }
        return v // 만들었으니 만든거 return
        //return super.getView(position, convertView, parent)
    }
}
