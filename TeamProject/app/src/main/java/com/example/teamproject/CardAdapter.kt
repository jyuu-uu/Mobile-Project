package com.example.teamproject



import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.text.Layout
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_add_travel.*
import org.w3c.dom.Text
import java.security.AccessController.getContext


class CardAdapter(val context: Context,val items:ArrayList<schedule>)
    :RecyclerView.Adapter<CardAdapter.ViewHolder>(){
    val my_intent = Intent(this.context, Receiver::class.java)
    lateinit var pendingIntent: PendingIntent
    lateinit var alarm_manager: AlarmManager
    lateinit var alarm_timepicker: TimePicker
    lateinit var alarm_datepicker: DatePicker
    //lateinit var date:String
    //var dates:MutableList<Int> = mutableListOf()
    //var times:MutableList<Int> = mutableListOf()
    //lateinit var time:String

    var check1=false
    var check2=false

    fun removeitem(pos:Int){
        Log.e("pos",pos.toString())
        var sindex=items[pos].sno
        items.removeAt(pos)
        schedules.removeAt(pos)

        val db = FirebaseFirestore.getInstance()
        var new: MutableMap<String, Any>? = null
        new = mutableMapOf()
        new["s_id"] = 0
        new["s_todo"] = "null"
        new["s_time"] = "null"
        new["s_alarm"] =false
        new["t_id"] = 0
        //데이터준비
        if (db != null) {
//        val newCount = String.format("%03d", count + 1)
            db!!.collection("Schedule").document("schedule"+ sindex.toString())
                .set(new)
        }
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
        alarm_manager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        var layoutInflater:LayoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //val dialogView=layoutInflater.inflate(R.layout.add_alarm, null)
        //val dialogView=layoutInflater.inflate(R.layout.add_schedule_dialog, null)

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.time.text=items.get(position).time.toString()
        holder.todo.text=items.get(position).todo.toString()
        if(items.get(position).alarm)
            holder.icon.setImageResource(R.drawable.alarm_on)
        else
            holder.icon.setImageResource(R.drawable.alarm_off)
        val ad = AlertDialog.Builder(context)
        val ad2 = AlertDialog.Builder(context)
        var tperiod=travels[index].period
        val separate= tperiod.split("~")
        var date = mutableListOf<String>()
        date.add(separate[0])
        date.add(separate[1])
        val separate1=date[0].split("/")
        val separate2=date[1].split("/")
        var cal1=Calendar.getInstance()
        var cal2=Calendar.getInstance()
        cal1.set(separate1[0].toInt(),separate1[1].toInt()-1,separate1[2].toInt())
        cal2.set(separate2[0].toInt(),separate2[1].toInt()-1,separate2[2].toInt())
        var dialog=ad.create()
        holder.todo.setOnClickListener {
            val dialogView=layoutInflater.inflate(R.layout.add_alarm, null)
            val dialogwhat = dialogView.findViewById<EditText>(R.id.schedule_what)
            val dialogtext1 = dialogView.findViewById<TextView>(R.id.textv1)
            val dialogtext2 = dialogView.findViewById<TextView>(R.id.textv2)
            var datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)
            datePicker.setMinDate(cal1.timeInMillis)
            datePicker.setMaxDate(cal2.timeInMillis)
            var timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)
            dialogwhat.setText(items.get(position).todo)
            ad2.setView(dialogView)
                .setPositiveButton("수정"){ dialogInterface, i ->
                    val db = FirebaseFirestore.getInstance()
                    var edit: MutableMap<String, Any>? = null
                    edit = mutableMapOf()
                    edit["s_todo"] = dialogwhat.text.toString()
                    edit["s_time"] = datePicker.year.toString()+"/"+(datePicker.month+1)+"/"+datePicker.dayOfMonth+"/"+timePicker.hour.toString()+":"+timePicker.minute

                    //데이터준비
                    if (db != null) {
//        val newCount = String.format("%03d", count + 1)
                        db!!.collection("Schedule").document("schedule"+ items.get(position).sno.toString())
                            .set(edit, SetOptions.merge())
                    }
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show()
                    //dialogInterface.dismiss()
                    dialog.dismiss()
                }
                .show()
        }
        holder.icon.setOnClickListener {        //알람설정
            val dialogView=layoutInflater.inflate(R.layout.add_alarm, null)
            val dialogwhat = dialogView.findViewById<EditText>(R.id.schedule_what)
            val dialogtext1 = dialogView.findViewById<TextView>(R.id.textv1)
            val dialogtext2 = dialogView.findViewById<TextView>(R.id.textv2)
            //val dialogdate = dialogView.findViewById<EditText>(R.id.schedule_date)
            //val dialogtime = dialogView.findViewById<EditText>(R.id.schedule_time)
            //val dialogwhen=dialogView.findViewById<EditText>(R.id.schedule_when)
            alarm_timepicker = dialogView.findViewById(R.id.time_picker)
            alarm_datepicker = dialogView.findViewById(R.id.date_picker)
            dialogtext1.setText("title to alarm")
            dialogtext2.setText("time to alarm")
            if(!items.get(position).alarm){
                ad.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->
                        //if(dialogwhat.text.toString()!=""&&dialogwhen.text.toString()!=""){
                        //if(dialogwhat.text.toString()!=""&&check1==true&&check2==true){
                        if(dialogwhat.text.toString()!=""){
                            //알람추가하기
                            holder.icon.setImageResource(R.drawable.alarm_on)
                            for(i in 0..schedules.size){
                                if(items.get(position).sno== schedules.get(i).sno){
                                    schedules.get(i).alarm=true
                                    break
                                }
                            }
                            my_intent.putExtra("state","alarm on");
                            my_intent.putExtra("todo",items.get(position).todo)
                            my_intent.putExtra("what",dialogwhat.text.toString())
                            my_intent.putExtra("s_id",items.get(position).sno.toString())
                            Log.e("sno",items.get(position).sno.toString())
                            //pendingIntent = PendingIntent.getBroadcast(context, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            val calendar = Calendar.getInstance()
                            //알람시간 calendar에 set해주기
                            //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 20, 27, 0)
                            calendar.set(alarm_datepicker.year, alarm_datepicker.month, alarm_datepicker.dayOfMonth, alarm_timepicker.hour, alarm_timepicker.minute, 0)
                            var sqlite = SQLite(context,"Alarm")
                            sqlite.openDatabase("USER")
                            //val dateFormat = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                            //val timeFormat = SimpleDateFormat("hh:mm", java.util.Locale.getDefault())
                            //val date = dateFormat.parse(alarm_datepicker.year.toString()+"-"+alarm_datepicker.month+"-"+alarm_datepicker.dayOfMonth)
                            //val time = dateFormat.parse(alarm_timepicker.hour.toString()+":"+alarm_timepicker.minute+":00")

                            var date=alarm_datepicker.year.toString()+"-"+alarm_datepicker.month+"-"+alarm_datepicker.dayOfMonth
                            var time=alarm_timepicker.hour.toString()+":"+alarm_timepicker.minute//+":00"
                            Log.e("datepicker",date)
                            Log.e("timepicker",time)
                            sqlite.insertData(date,time,items.get(position).todo,dialogwhat.text.toString(),items.get(position).sno.toString())
                            sqlite.readAlarm(context)
                            //sqlite.dropTable("Alarm")
                            //	db date type 2008-11-11 / time hh:mm:ss
                            // 알람셋팅
                            //alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent)
                            Toast.makeText(context,"알람 추가",Toast.LENGTH_SHORT).show()
                            items.get(position).alarm=true
                            var sindex=-1
                            for(i in 0..schedules.size){
                                if(items.get(position).sno== schedules.get(i).sno){
                                    sindex=items.get(position).sno
                                    break
                                }
                            }
                            val db = FirebaseFirestore.getInstance()
                            //데이터준비
                            if (db != null) {
                                var new: MutableMap<String, Any>? = null
                                new = mutableMapOf()
                                new["s_alarm"] = true
                                // Add a new document with a generated ID

//        val newCount = String.format("%03d", count + 1)
                                db!!.collection("Schedule").document("schedule"+ sindex.toString())
                                    .set(new, SetOptions.merge())
                                    .addOnSuccessListener { Log.e("database", "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.e("database", "Error writing document") }
                            }

                        }else{
                            Toast.makeText(context,"알람 추가 실패",Toast.LENGTH_SHORT).show()
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                        Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show()
                        //dialogInterface.dismiss()
                        dialog.dismiss()
                    }
                    .show()

            }
            else{
                var sqlite = SQLite(context,"Alarm")
                sqlite.openDatabase("USER")
                var a_id=sqlite.searchAlarm(items.get(position).todo, "Alarm")
                sqlite.deleterow(items.get(position).todo.toString(),"Alarm")
                Toast.makeText(context,"알람이 삭제되었습니다.",Toast.LENGTH_SHORT).show()
                holder.icon.setImageResource(R.drawable.alarm_off)
                items.get(position).alarm=false
                var sindex=-1
                for(i in 0..schedules.size){
                    if(items.get(position).sno== schedules.get(i).sno){
                        sindex=items.get(position).sno
                        schedules.get(i).alarm=false
                        break
                    }
                }
                pendingIntent = PendingIntent.getBroadcast(context, a_id, my_intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarm_manager.cancel(pendingIntent)

                val db = FirebaseFirestore.getInstance()
                //데이터준비
                if (db != null) {
                    var new2: MutableMap<String, Any>? = null
                    new2 = mutableMapOf()
                    new2["s_alarm"] = false
                    // Add a new document with a generated ID

//        val newCount = String.format("%03d", count + 1)
                    db!!.collection("Schedule").document("schedule"+ sindex.toString())
                        .set(new2, SetOptions.merge())
                        .addOnSuccessListener { Log.e("database", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.e("database", "Error writing document") }
                }
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