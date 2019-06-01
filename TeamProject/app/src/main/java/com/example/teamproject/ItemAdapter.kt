package com.example.teamproject


import android.content.ClipData
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ItemAdapter(val items:ArrayList<Item>) :
    View.OnTouchListener,
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    val listener= DragListener()
    lateinit var itemListener:DragItemListener

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_MOVE->{
                val data = ClipData.newPlainText("","")
                val shadow = View.DragShadowBuilder(v)
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    v?.startDragAndDrop(data, shadow, v, 0)
                }else{
                    v?.startDrag(data, shadow, v, 0)
                }
                return true
            }
        }
        return false
    }

    interface OnItemClickListener{
        fun OnItemClick(holder: ViewHolder, view: View, data: Item, position: Int)
    }

    var itemClickListener : ItemAdapter.OnItemClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {//layout에 해당하는 정보를 만듬
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_row, p0, false)
        itemListener = DragItemListener(items.get(p1))
        v.setOnDragListener(itemListener)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {//데이터하고 view하고 연결
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        p0.name.text = items.get(p1).iname.toString()//adapter에서는 data class의 data와 xml을 연결
        p0.text.text = items.get(p1).itext
        p0.icon.setImageResource(items.get(p1).idrawable)
        p0.cardView.setTag(p1)
        p0.cardView.setOnTouchListener(this)
        p0.cardView.setOnDragListener(listener)
    }
    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //생성자가 primary생성자만 있다. 이때는 초기화를 init블럭을 이용한다
        var name: TextView
        var text:TextView
        var icon: ImageView
        var cardView: android.support.v7.widget.CardView

        init {
            name = itemView.findViewById(R.id.i_name)
            text = itemView.findViewById(R.id.i_text)
            icon = itemView.findViewById(R.id.i_icon)
            cardView = itemView.findViewById(R.id.cardView)

            itemView.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.OnItemClick(this, it, items[position], position)
            }
        }
    }
    fun getList() : ArrayList<Item>{
        return items
    }
    fun updateList(list:ArrayList<Item>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getDragInstance():DragListener{
        if(listener != null){
            val listener_new = DragListener()
            return listener_new
        }
        else{
            return listener
        }
    }
}
//    fun removeItem(pos:Int){//position정보, swipe하면 없어지게
//        items.removeAt(pos)
//        notifyItemRemoved(pos)
//    }
//
//    fun moveItem(pos1:Int, pos2:Int){
//        val item1 = items[pos1]//position
//        val item2 = items[pos2]
//        //지우고 다시 넣어준다.
//        items.removeAt(pos1)//옮기려는걸 먼저 지운다
//        items.add(pos2, item1)//그리고 원하는 pos에 추가하면 알아서 밑에 있는것은 밀리게됨
//        //position이 바뀌었음을 알려준다.
//        notifyItemMoved(pos1, pos2)
//    }

