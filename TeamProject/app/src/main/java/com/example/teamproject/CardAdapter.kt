package com.example.teamproject



import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.text.Layout
import org.w3c.dom.Text
import java.security.AccessController.getContext


class CardAdapter(val context: Context,val items:ArrayList<schedule>)
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
        if(items.get(position).alarm)
            holder.icon.setImageResource(R.drawable.alarm_on)
        else
            holder.icon.setImageResource(R.drawable.alarm_off)

        holder.icon.setOnClickListener {        //알람설정
            var layoutInflater:LayoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView=layoutInflater.inflate(R.layout.add_schedule_dialog, null)
            val dialogwhat = dialogView.findViewById<EditText>(R.id.schedule_what)
            val dialogtext1 = dialogView.findViewById<TextView>(R.id.textv1)
            val dialogtext2 = dialogView.findViewById<TextView>(R.id.textv2)
            val dialogwhen = dialogView.findViewById<EditText>(R.id.schedule_when)
            //일단 걍 준비물,시간 입력하게 해둠-->체크리스트에서 준비물 선택, 타임피커로 시간 선택

            dialogtext1.setText("title to alarm")
            dialogtext2.setText("time to alarm")
            val ad = AlertDialog.Builder(context)
            if(!items.get(position).alarm){
                ad.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->
                        if(dialogwhat.text.toString()!=""&&dialogwhen.text.toString()!=""){
                            //알람추가하기
                            holder.icon.setImageResource(R.drawable.alarm_on)
                            for(i in 0..schedules.size){
                                if(items.get(position).sno== schedules.get(i).sno){
                                    schedules.get(i).alarm=true
                                    break
                                }
                            }
                            Toast.makeText(context,"알람 추가",Toast.LENGTH_SHORT).show()

                        }else{
                            items.get(position).alarm=false
                            holder.icon.setImageResource(R.drawable.alarm_off)
                            Toast.makeText(context,"알람 추가 실패",Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }
                    .show()
            }
            else{
                Toast.makeText(context,"알람이 삭제되었습니다.",Toast.LENGTH_SHORT).show()
                holder.icon.setImageResource(R.drawable.alarm_off)
                items.get(position).alarm=false
            }

//            if(!items.get(position).alarm){
//                holder.icon.setImageResource(R.drawable.alarm_on)
//                items.get(position).alarm=true
//                var sdate= Date(119,4,22,9,14)
//                var now=Date()
//                val full_sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm a")
//                if(full_sdf.format(now)==full_sdf.format(sdate))
//                    holder.time.text=full_sdf.format(sdate)+full_sdf.format(now)
//            }
//            else{
//                holder.icon.setImageResource(R.drawable.alarm_off)
//                items.get(position).alarm=false
//            }

        }

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