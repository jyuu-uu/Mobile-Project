package com.example.teamproject


import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myreportaftertravel.MyMarketAdapter
import com.example.myreportaftertravel.MyObjectAdapter
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.fragment_dreview_frag3.*
import kotlinx.android.synthetic.main.review_register.*

class DReviewFrag3 : Fragment() {

    var check = false
    var t_id: Int = -1
    lateinit var v: View
    lateinit var db:Firestore
    lateinit var data:ArrayList<String>
    lateinit var adapter:DReview_sAdapter

    companion object {
        fun start(id: Int) : DReviewFrag3 {
            val d = DReviewFrag3()
            d.t_id = id
            return d
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_dreview_frag3, container, false)
        db = Firestore.create(v.context)

        setData()

        return v
    }
    fun setData() {
        if (!check) {
            check = true
            data = ArrayList<String>()
            db!!.db!!.collection("Review").whereEqualTo("t_id", t_id).get()
                .addOnSuccessListener {
                    val a = it.documents
                    for (k in a) {
                        Log.e("몇개야?", "$k")
                        val b= k.get("r_object")
                        if(b != null) {
                            val c = b as ArrayList<String>
                            for(i in c)
                                data.add(i)
                            Log.e("나 들어왔어!", c.toString())

                        }
                            }
                    initLayout()
                }
        }
    }

    fun initLayout(){
        val layoutManager = LinearLayoutManager(super.getContext(), LinearLayoutManager.VERTICAL, false)

        recycler_object.layoutManager = layoutManager
        var infoArr = mutableListOf<String>()

//        val db =  Firestore.create(this.activity!!.applicationContext)
//        var str = db!!.db!!.collection("Review").whereEqualTo("t_id",a).get()
//        .addOnSuccessListener {
//
//        }
        infoArr = data
        //adapter = MyMarketAdapter(infoArr, this)
        recycler_object.adapter = MyObjectAdapter(infoArr)
    }


}
