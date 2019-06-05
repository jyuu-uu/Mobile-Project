package com.example.teamproject

import java.io.Serializable

data class Item(var i_drawable: Int, val i_name:String, val i_tnum:Int, val i_ugender:Boolean, val i_uage:Int) : Serializable {
}
