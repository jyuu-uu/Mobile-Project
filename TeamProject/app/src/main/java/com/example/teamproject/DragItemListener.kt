package com.example.teamproject

import android.view.DragEvent
import android.view.View
import android.widget.ListView

class DragItemListener(item: Item): View.OnDragListener {
    private var me = item

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event?.action) {
            DragEvent.ACTION_DROP -> {
                val pass = event?.localState as PassObject
                var view = pass.v
                val passedItem = pass.i
                val srcList = pass.list
                val oldParent = view.parent as ListView
                val srcAdapter: ItemAdapter = oldParent.adapter as ItemAdapter

                val newParent = v?.parent as ListView
                val destAdapter = newParent.adapter as ItemAdapter
                val destList = destAdapter.getList()

                val removeLocation = srcList.indexOf(passedItem)
                val insertLocation = destList.indexOf(me)

                if (srcList != destList || removeLocation != insertLocation) {
                    srcList.remove(passedItem)
                    destList.add(insertLocation, passedItem)

                    srcAdapter.notifyDataSetChanged()
                    destAdapter.notifyDataSetChanged()
                }
            }
        }
        return true
    }

    inner class PassObject(val v: View, val i:Item, val list:ArrayList<Item>) {
    }
}