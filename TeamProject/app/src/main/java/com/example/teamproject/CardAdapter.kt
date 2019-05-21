package com.example.teamproject


//class CardAdapter (context: Context, val resource:Int, var list:ArrayList<schedule>)
//    : RecyclerView.Adapter<schedule>(context,resource,list)
//{
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var v: View?=convertView
//        if(v==null){
//            var vi=context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            v=vi.inflate(R.layout.fragment_list1_card, null)
//
//        }
//        val p=list.get(position)
//        v!!.findViewById<TextView>(R.id.time).text=p.time
//        v!!.findViewById<TextView>(R.id.schedule).text=p.todo
//
//        return v
//        //return super.getView(position, convertView, parent)
//    }
//}


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CardAdapter(val items:ArrayList<schedule>)
    :RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    fun removeitem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
    fun moveitem(pos1:Int, pos2:Int){
        val item1=items.get(pos1)
        items.removeAt(pos1)
        items.add(pos2, item1)
        notifyItemMoved(pos1,pos2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v= LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list1_card, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.time.text=items.get(position).time.toString()
        holder.todo.text=items.get(position).todo.toString()
//        if(items.get(position).wifi){
//            holder.icon.setImageResource(R.drawable.coffee_wifi)
//        }else{
//            holder.icon.setImageResource(R.drawable.coffee_nowifi)
//        }

    }

    inner class ViewHolder(itemView:View)
        :RecyclerView.ViewHolder(itemView){
        var time:TextView
        var todo:TextView
        var icon:ImageView
        init{
            time=itemView.findViewById(R.id.time)
            todo=itemView.findViewById(R.id.schedule)
            icon=itemView.findViewById(R.id.listicon)

        }

    }
}
