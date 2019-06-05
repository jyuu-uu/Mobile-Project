package com.example.teamproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide.init
import kotlinx.android.synthetic.main.review_register.*

class ReviewRegisterView:AppCompatActivity() {

    var weather = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_register)
        var i=intent
        index=i.getIntExtra("tno",-1)
        //Toast.makeText(this, index.toString(),Toast.LENGTH_SHORT).show()

        init()
    }
    fun init(){
        review_country.text = index.toString()
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
    }
}