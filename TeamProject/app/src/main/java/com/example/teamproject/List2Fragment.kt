package com.example.teamproject

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_list2.*

class List2Fragment : Fragment() {
    lateinit var item1:ArrayList<Item>
    lateinit var item2:ArrayList<Item>
    lateinit var itemAdapter1: ItemAdapter
    lateinit var itemAdapter2: ItemAdapter
    lateinit var newItem:Item
    var v:View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list2, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        item1 = arrayListOf()
        item2 = arrayListOf()
        init()

    }

    fun init(){
        initOne()
        initTwo()

        var touchHelper1 = object : ItemTouchHelper.SimpleCallback( 3, ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                val dragged_item_pos = item1[p0.adapterPosition]
                item2.add(dragged_item_pos)
                item1.removeAt(p0.adapterPosition)
                itemAdapter1.notifyItemRemoved(p0.adapterPosition)
                itemAdapter2.notifyDataSetChanged()
            }
        }
        val itemTouchHelper1 = ItemTouchHelper(touchHelper1)
        itemTouchHelper1.attachToRecyclerView(v!!.findViewById(R.id.listView1))


        var touchHelper2 = object : ItemTouchHelper.SimpleCallback( 3, ItemTouchHelper.LEFT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                val dragged_item_pos = item2[p0.adapterPosition]
                item1.add(dragged_item_pos)
                item2.removeAt(p0.adapterPosition)
                itemAdapter1.notifyDataSetChanged()
                itemAdapter2.notifyItemRemoved(p0.adapterPosition)
            }
        }
        val itemTouchHelper2 = ItemTouchHelper(touchHelper2)
        itemTouchHelper2.attachToRecyclerView(v!!.findViewById(R.id.listView2))
    }

    fun initOne(){
//        area1 = activity!!.findViewById(R.id.pane1)
//        area1.setOnDragListener(DragListener())
        val listView1 = v!!.findViewById<RecyclerView>(R.id.listView1)
        val layoutManager1 = LinearLayoutManager(context)

        listView1.layoutManager = layoutManager1
        itemAdapter1 = ItemAdapter(item1)
        listView1.adapter = itemAdapter1
        itemAdapter1.itemClickListener = object :ItemAdapter.OnItemClickListener{
            override fun OnItemClick(holder: ItemAdapter.ViewHolder, view: View, data: Item, position: Int) {
                Toast.makeText(view.context, data.i_name, Toast.LENGTH_SHORT).show()
            }
        }
//        listView1.setOnDragListener(itemAdapter1.getDragInstance())
    }
    fun initTwo(){
//        area2 = activity!!.findViewById(R.id.pane2)
//        area2.setOnDragListener(DragListener())
        val listView2 = v!!.findViewById<RecyclerView>(R.id.listView2)
        val layoutManager2 = LinearLayoutManager(context)

        item2.addAll(item)
        listView2.layoutManager = layoutManager2
        itemAdapter2 = ItemAdapter(item2)
        listView2.adapter = itemAdapter2
        itemAdapter2.itemClickListener = object :ItemAdapter.OnItemClickListener{
            override fun OnItemClick(holder: ItemAdapter.ViewHolder, view: View, data: Item, position: Int) {
                Toast.makeText(view.context, data.i_name, Toast.LENGTH_SHORT).show()
            }
        }
        add()

//        listView2.setOnDragListener(itemAdapter2.getDragInstance())
    }
    lateinit var db:Firestore
    fun add() {
        db = Firestore.create(context!!)
        //데이터준비
        val ad = AlertDialog.Builder(v!!.context)
        additemBtn.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.add_item, null)
            var imgload = dialogView.findViewById<ImageView>(R.id.img_load)
            var imgname = dialogView.findViewById<EditText>(R.id.itemname)
            ad.setView(dialogView)
                .setPositiveButton("추가") { dialog, id->
                    imgload.setOnClickListener {
                        //====================icon 추가
                        Toast.makeText(context, "빈칸을 채워주세요.", Toast.LENGTH_LONG).show()
                    }

                    if (imgname.text.toString() == "") {
                        Toast.makeText(context, "빈칸을 채워주세요.", Toast.LENGTH_LONG).show()
                    } else {
                        //var uid:String? = null
                        if(db != null){
                            db?.db?.collection("User")!!.document(MainActivity.User.toString()).get()
                                .addOnSuccessListener {
                                    var gender = it.get("u_gender").toString().toBoolean()
                                    var age = it.get("u_age").toString().toInt()
                                    newItem = Item(R.drawable.ic_1_black, imgname.text.toString(), item_tnum, gender, age)
                                    db?.db?.collection("Item")!!.document("item" + (items.size + 1).toString())
                                        .set(newItem)
                                        .addOnSuccessListener { Log.e("database", "DocumentSnapshot successfully written!") }
                                        .addOnFailureListener { e -> Log.e("database", "Error writing document") }

                                    addItem(newItem)

                                }

                            Toast.makeText(context, "일정 추가 완료! ", Toast.LENGTH_SHORT).show()
                        }
                        else{
                        }
                    }
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()
        }
    }
    fun addItem(plusitem:Item){
        item2.add(plusitem)
        listView2.adapter!!.notifyDataSetChanged()
    }

}
