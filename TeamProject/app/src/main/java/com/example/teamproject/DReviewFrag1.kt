package com.example.teamproject


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.fragment_dreview_frag1.view.*

class DReviewFrag1 : Fragment() {

    var t_id: Int = -1
    lateinit var v: View
    lateinit var db:Firestore

    lateinit var t_where: String
    lateinit var t_when:String
    var t_who = -1
    lateinit var t_cost:String
    var isfav = false

    companion object {
        fun start(t_where: String, t_when: String, t_who: Int, t_cost: String, isfav: Boolean, id: Int)
            :DReviewFrag1{
                  val d = DReviewFrag1()
            d.t_where = t_where
            d.t_when = t_when
            d.t_who = t_who
            d.t_cost = t_cost
            d.isfav = isfav
            d.t_id = id
            return d
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_dreview_frag1, container, false)
        db = Firestore.create(v.context)!!
        all()
        return v
    }

    fun all(){
        v.dreview_where.text = t_where
        v.dreview_when.text = t_when
        v.dreview_who.text = t_who.toString() + " 명"
        v.dreview_cost.text = t_cost
        v.dreview_isfav.isChecked = isfav
        t_id = id
        v.dreview_isfav.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                db!!.db!!.collection("User").document(MainActivity.User.toString()).get()
                    .addOnCompleteListener {
                        val a = it?.result!!.data?.get("fav") as ArrayList<Long>
                        val updat = ArrayList<Int>()
                        Log.e("배열값", "$a")
                        var isfav = false

                        for (k in a) {
                            updat.add(k.toInt())
                        }
                        updat.remove(t_id)
                        db!!.db!!.collection("User").document(MainActivity.User!!)
                            .update("fav", FieldValue.delete())
                        db!!.db!!.collection("User").document(MainActivity.User!!)
                            .update("fav",updat)
                    }
            }
            else {
                db!!.db!!.collection("User").document(MainActivity.User.toString()).get()
                    .addOnCompleteListener {
                        val a = it?.result!!.data?.get("fav") as ArrayList<Long>
                        val updat = ArrayList<Int>()
                        Log.e("배열값", "$a")
                        var isfav = false

                        for (k in a) {
                            updat.add(k.toInt())
                        }
                        updat.add(t_id)
                        db!!.db!!.collection("User").document(MainActivity.User!!)
                            .update("fav", FieldValue.delete())
                        db!!.db!!.collection("User").document(MainActivity.User!!)
                            .update("fav", updat)
                    }
            }
        }
    }
}
