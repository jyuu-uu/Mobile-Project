package com.example.teamproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fav.view.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.google.firebase.firestore.*
import kotlin.collections.ArrayList


class FavFragment : Fragment() {

    var db: Firestore? = null
    var adapter: FavAdapter? = null
    var User: String? = null
    var key = mutableListOf<Int>()
    var data = mutableListOf<FavData>()
    var v: View? = null

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

                    val a = it.get("fav")
                    if(a != null) {
                        var res = a as ArrayList<Long>
                        for (k in res) {
                            //                       Log.e("fav", "$k")
                            key.add(k.toBigInteger().toInt())
                        }
                        findData2()
                    }
                }
        }
    }

    var flag = 0
    fun findData2() {
        if (key.size != 0) {
            db!!.db?.collection("Travel")?.whereEqualTo("t_id", key[flag])?.get()
                ?.addOnCompleteListener { task ->
                    //              Log.e("즐겨찾기", "접속")
                    for (k in task.result!!) {
                        //                   Log.e("즐겨찾기", "$k")
                        data.add(
                            FavData(
                                k.get("t_id").toString().toInt(),
                                k.get("t_when").toString(),
                                k.get("t_where").toString(),
                                k.get("t_who").toString() + "명"
                            )
                        )
                    }
                    flag++
                    if (flag != key.size) {
                        findData2()
                    } else {
                        initAdapter()
                    }
                }
        }
    }
    fun initAdapter() {
        if (activity != null) {
            //레이아웃을 관리하는 매니저 객체가 필요
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            v!!.fav_list.layoutManager = layoutManager

 //           Log.e("fav오댭토", "$data")
            adapter = FavAdapter(data, activity!!.applicationContext, User!!)
            v!!.fav_list.adapter = adapter //data 정보를 갖는 어댑터를 생성하여 붙여줌
            // Context 정보, 수평수직 정보, 순서 정보
            v!!.fav_list.layoutManager = layoutManager
            // recyclerView를 위한 매니저이므로, 붙여줌

            initSwipe()
        }
    }

    fun initSwipe(){
        var touchHelper1 = object : ItemTouchHelper.SimpleCallback( 3, ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                val drag = p0.adapterPosition
                Log.e("선택뷰 좌표","$drag")
                key.removeAt(drag)
                data.removeAt(drag)
                db!!.db!!.collection("User").document(User!!)
                    .update("fav", FieldValue.delete())
                db!!.db!!.collection("User").document(User!!)
                    .update("fav",key)

                adapter!!.notifyItemRemoved(drag)
            }
        }
        val itemTouchHelper1 = ItemTouchHelper(touchHelper1)
        itemTouchHelper1.attachToRecyclerView(v!!.fav_list)
    }
}
