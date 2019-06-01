package com.example.teamproject

import android.content.ClipData
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list2.*


class List2Fragment : Fragment() {
    lateinit var item1:ArrayList<Item>
    lateinit var item2:ArrayList<Item>
    lateinit var itemAdapter1: ItemAdapter
    lateinit var itemAdapter2: ItemAdapter
    lateinit var area1: LinearLayout
    lateinit var area2: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list2, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init(){
        item1 = arrayListOf()
        item2 = arrayListOf()

        item1.add(Item(R.drawable.ic_1, "a", "a-"))
        item1.add(Item(R.drawable.ic_1, "b", "b-"))
        item1.add(Item(R.drawable.ic_1, "c", "c-"))

        item2.add(Item(R.drawable.ic_1, "d", "d-"))
        item2.add(Item(R.drawable.ic_1, "e", "e-"))
        item2.add(Item(R.drawable.ic_1, "f", "f-"))

        initOne()
        initTwo()

        var touchHelper = object : ItemTouchHelper.SimpleCallback( ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.ACTION_STATE_SWIPE){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                val dragged_item_pos = p1.adapterPosition
                val target_item_pos = p2.adapterPosition

                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelper)
        itemTouchHelper.attachToRecyclerView(listView1)
        itemTouchHelper.attachToRecyclerView(listView2)
    }

    fun initOne(){
        area1 = activity!!.findViewById(R.id.pane1)
        area1.setOnDragListener(DragListener())
        val listView1 = activity!!.findViewById<RecyclerView>(R.id.listView1)
        val layoutManager1 = LinearLayoutManager(context)

        listView1.layoutManager = layoutManager1
        itemAdapter1 = ItemAdapter(item1)
        listView1.adapter = itemAdapter1
        itemAdapter1.itemClickListener = object :ItemAdapter.OnItemClickListener{
            override fun OnItemClick(holder: ItemAdapter.ViewHolder, view: View, data: Item, position: Int) {
                Toast.makeText(view.context, data.iname, Toast.LENGTH_SHORT).show()
            }
        }
        listView1.setOnDragListener(itemAdapter1.getDragInstance())
    }
    fun initTwo(){
        area2 = activity!!.findViewById(R.id.pane2)
        area2.setOnDragListener(DragListener())
        val listView2 = activity!!.findViewById<RecyclerView>(R.id.listView2)
        val layoutManager2 = LinearLayoutManager(context)

        listView2.layoutManager = layoutManager2
        itemAdapter2 = ItemAdapter(item2)
        listView2.adapter = itemAdapter2
        itemAdapter2.itemClickListener = object :ItemAdapter.OnItemClickListener{
            override fun OnItemClick(holder: ItemAdapter.ViewHolder, view: View, data: Item, position: Int) {
                Toast.makeText(view.context, data.iname, Toast.LENGTH_SHORT).show()
            }
        }
        listView2.setOnDragListener(itemAdapter2.getDragInstance())
    }


}
