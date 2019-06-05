package com.example.teamproject

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.review_register.*

class ReviewRegisterView:AppCompatActivity() {

    var weather = 0
    var i=intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_register)
        //Toast.makeText(this, index.toString(),Toast.LENGTH_SHORT).show()

        init()
    }

    var count = -1
    fun init(){
//        review_country.text = i.getStringExtra("tno")
        review_country.text = intent.getExtras().getString("tno")
        review_weather_img.setImageResource(R.drawable.sun)
        review_weather.setOnClickListener {
            when(weather){
                0->{
                    review_weather_img.setImageResource(R.drawable.rainy)
                    weather++
                }
                1->{
                    review_weather_img.setImageResource(R.drawable.cloud)
                    weather++
                }
                else->{
                    review_weather_img.setImageResource(R.drawable.sun)
                    weather = 0
                }
            }
        }
        review_star.setOnClickListener {
            when(review_star.text.toString().toDouble()){
                0.0->{
                    review_star.text = "0.5"
                }
                0.5->{
                    review_star.text = "1"
                }
                1.0->{
                    review_star.text = "1.5"
                }
                1.5->{
                    review_star.text = "2"
                }
                2.0->{
                    review_star.text = "2.5"
                }
                2.5->{
                    review_star.text = "3"
                }
                3.0->{
                    review_star.text = "3.5"
                }
                3.5->{
                    review_star.text = "4"
                }
                4.0->{
                    review_star.text = "4.5"
                }
                4.5->{
                    review_star.text = "5"
                }
                5.0->{
                    review_star.text = "0"
                }
                else->{
                    review_star.text = "0"
                }
            }
        }
        btn_register_review.setOnClickListener {
            val db = Firestore.create(applicationContext)
            db!!.db!!.collection("Review").get().addOnCompleteListener {
                count = it.result!!.size()
            addTuple()

            }
    }

}
    fun addTuple(){
        val db = Firestore.create(applicationContext)
        var m = mutableMapOf<String,Any?>()
        m["t_id"] = intent.getIntExtra("t_id", -1)
        m["r_country"] = review_country.text.toString()
        var str = review_object.text.toString().trim().split(",")
        m["r_object"] = str
        m["r_star"] = review_star.text.toString()
        m["r_text"] = review_text.text.toString()
        m["r_text_name"] = review_name.text.toString()
        m["r_weather"] = weather


        db!!.db!!.collection("Review").document("review"+ (count.toInt()+1).toString()).set(m)
            .addOnSuccessListener {
                changeFin()
            }
    }

    fun changeFin(){
        val a = intent.getIntExtra("t_id", -1)
        val db = Firestore.create(applicationContext)

        db!!.db!!.collection("Travel").whereEqualTo("t_id",a).get()
            .addOnSuccessListener {
                for(k in it.documents){
                    val b = k.id
                    Log.e("나는 비다ㅏㅏㅏ", b)
                    db!!.db!!.collection("Travel").document(b).update("t_fin",true)
                }
            }
    }
}