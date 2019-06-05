package com.example.teamproject

import java.io.Serializable

data class Item(var idrawable: Int, val iname:String, val itnum:Int, val iugender:Boolean, val iuage:Int) : Serializable {
}
