package com.example.teamproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.fragment_dreview_frag1.view.*
import kotlinx.android.synthetic.main.fragment_dreview_frag2.view.*

class DReviewFrag2 : Fragment() {

    var check = false
    var t_id: Int = -1
    lateinit var v: View
    lateinit var db:Firestore
    lateinit var data:ArrayList<DReview_sData>
    lateinit var adapter:DReview_sAdapter

    companion object {
        fun start(id: Int) : DReviewFrag2 {
            val d = DReviewFrag2()
               d.t_id = id
            return d
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_dreview_frag2, container, false)
        db = Firestore.create(v.context)

        setData()

        return v
    }

    fun setData() {
        if (!check) {
            check = true
            data = ArrayList<DReview_sData>()
            db!!.db!!.collection("Schedule").whereEqualTo("t_id", t_id).get()
                .addOnSuccessListener {
                    val a = it.documents
                    Log.e("abc", "bac")
                    for (k in a) {
                        Log.e("몇개야?", "$k")
                        data.add(DReview_sData(k.get("s_todo").toString(), k.get("s_time").toString()))
                    }
                    initAdapter()
                }
        }
        else
            initAdapter()
    }

    fun initAdapter(){

        adapter = DReview_sAdapter(activity!!.applicationContext,R.layout.dreview_schedule_form,data)

        v.dreview_s.adapter = adapter
    }
}
