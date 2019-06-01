package com.example.teamproject


import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.DragEvent
import android.view.View
import android.widget.Toast
import com.example.teamproject.ItemAdapter
import com.example.teamproject.R

class DragListener:View.OnDragListener {
    private var isDropped = false
    private var positionTarget = -1

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        var area = " "
        when (v!!.id) {
            R.id.listView1 -> {
                area = "area1"
            }
            R.id.listView2 -> {
                area = "area2"
            }
            else->{
                area = v!!.id.toString()
            }
        }
        when (event?.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                Toast.makeText(v?.context, area, Toast.LENGTH_SHORT).show()
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Toast.makeText(v?.context, area, Toast.LENGTH_SHORT).show()
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Toast.makeText(v?.context, area, Toast.LENGTH_SHORT).show()
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Toast.makeText(v?.context, area, Toast.LENGTH_SHORT).show()
            }
            DragEvent.ACTION_DROP -> {
                Toast.makeText(v?.context, area, Toast.LENGTH_SHORT).show()
            }
        }

        when (event?.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                positionTarget = -1
//                val pass = event!!.localState as PassObject
//                val passedItem = pass.i
//                val srcList = pass.list

                var viewid = v?.id
                var flItem = v?.findViewById<CardView>(R.id.cardView)
                var listView1 = v?.findViewById<RecyclerView>(R.id.listView1)
                var listView2 = v?.findViewById<RecyclerView>(R.id.listView2)
                var target: RecyclerView = RecyclerView(v!!.context)

                if (viewid!!.equals(flItem) || viewid!!.equals(listView1) || viewid!!.equals(listView2)) {
                    when (viewid) {
                        flItem?.id -> {
                            target = v!!.findViewById(R.id.cardView)
                        }
                        listView1?.id -> {
                            target = v!!.findViewById(R.id.listView1)
                        }
                        listView2?.id -> {
                            target = v!!.findViewById(R.id.listView2)
                        }
                    }
                    if (v != null) {
                        var source = v.parent as RecyclerView

                        var adapter: ItemAdapter = source.adapter as ItemAdapter
                        var positionSource = v.getTag() as Int
                        var idSource = source.id

                        var list = adapter.getList().get(positionSource)
                        val listSource: ArrayList<Item> = adapter.getList()

                        listSource.removeAt(positionSource)
                        adapter.updateList(listSource)
                        adapter.notifyDataSetChanged()

                        if (target != null) {
                            var adapterTarget = target.adapter as ItemAdapter
                            var listTarget: ArrayList<Item> = adapterTarget.getList()

                            if (positionTarget >= 0)
                                listTarget.add(positionTarget, list)
                            else
                                listTarget.add(list)

                            adapterTarget.updateList(listTarget)
                            adapterTarget.notifyDataSetChanged()
                        }

                    }
                }
            }
        }
        return true
    }
}
