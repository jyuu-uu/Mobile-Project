package com.example.teamproject

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.myreportaftertravel.MyCafe
import com.example.myreportaftertravel.MyCafeAdapter
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_fav.*
import kotlinx.android.synthetic.main.fragment_fav.view.*

class FavFragment : Fragment() {

    var db: Firestore? = null
    var adapter: FavAdapter? = null
    var User: String? = null
    var key = mutableListOf<String>()
    var data = mutableListOf<FavData>()
    var v :View?=null

    companion object {
        fun create(_User: String): FavFragment {
            val v = FavFragment()
            v.User = _User
            return v
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        db = Firestore.create(activity!!.applicationContext)
        findData()

        v = inflater.inflate(R.layout.fragment_fav, container, false)


        return v
    }

    fun findData() {
        if (db != null) {
            val isExist = db!!.db?.collection("User")?.document(User!!)?.get()
                ?.addOnSuccessListener {
                    val res = it.get("fav")
//                    val res2 = res.//a.result//!!.get("doc")
                    Log.e("fav", "$res")
                    for (k in (res as ArrayList<String>)) {
                        Log.e("fav", "$k")
                        key.add(k)
                    }
                    findData2()
                }
        }
    }

    fun findData2() {
        val isExist = db!!.db?.collection("Travel")?.get()
            ?.addOnSuccessListener {

                var i = 0
                for (k in it){
                    Log.e("fav2", "$k")
                    val e= k.get("t_id")
                    Log.e("fav 찾기","$e")
                    if(key[i]=="travel"+e.toString()){
                        data.add(FavData(key[i],k.get("t_when").toString(),
                            k.get("t_where").toString(),k.get("t_who").toString()+"명"))
                        i++
                        if(i == key.size)
                            break;
                    }
                }
                initAdapter()
            }
    }


    fun initAdapter() {
        //레이아웃을 관리하는 매니저 객체가 필요
        val layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        v!!.fav_list.layoutManager = layoutManager

        Log.e("fav오댭토","$data")
        adapter = FavAdapter(data, activity!!.applicationContext, User!!)
        v!!.fav_list.adapter = adapter //data 정보를 갖는 어댑터를 생성하여 붙여줌
        // Context 정보, 수평수직 정보, 순서 정보
        v!!.fav_list.layoutManager = layoutManager
        // recyclerView를 위한 매니저이므로, 붙여줌
    }
}
