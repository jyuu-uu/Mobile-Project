package com.example.teamproject

import android.content.ClipData
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_list2.*
import kotlinx.android.synthetic.main.fragment_list2.view.*


class List2Fragment : Fragment() {
    lateinit var item1:ArrayList<Item>
    lateinit var item2:ArrayList<Item>
    lateinit var itemAdapter1: ItemAdapter
    lateinit var itemAdapter2: ItemAdapter
    lateinit var area1: LinearLayout
    lateinit var area2: LinearLayout
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

        item1.addAll(item)
        listView1.layoutManager = layoutManager1
        itemAdapter1 = ItemAdapter(item1)
        listView1.adapter = itemAdapter1
        itemAdapter1.itemClickListener = object :ItemAdapter.OnItemClickListener{
            override fun OnItemClick(holder: ItemAdapter.ViewHolder, view: View, data: Item, position: Int) {
                Toast.makeText(view.context, data.iname, Toast.LENGTH_SHORT).show()
            }
        }
//        listView1.setOnDragListener(itemAdapter1.getDragInstance())
    }
    fun initTwo(){
//        area2 = activity!!.findViewById(R.id.pane2)
//        area2.setOnDragListener(DragListener())
        val listView2 = v!!.findViewById<RecyclerView>(R.id.listView2)
        val layoutManager2 = LinearLayoutManager(context)

        listView2.layoutManager = layoutManager2
        itemAdapter2 = ItemAdapter(item2)
        listView2.adapter = itemAdapter2
        itemAdapter2.itemClickListener = object :ItemAdapter.OnItemClickListener{
            override fun OnItemClick(holder: ItemAdapter.ViewHolder, view: View, data: Item, position: Int) {
                Toast.makeText(view.context, data.iname, Toast.LENGTH_SHORT).show()
            }
        }
//        listView2.setOnDragListener(itemAdapter2.getDragInstance())
    }
}
