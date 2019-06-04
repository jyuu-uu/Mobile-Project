package com.example.teamproject

import com.google.android.gms.maps.model.LatLng

data class Place_info(var name:String, var formatted_address:String, var location: LatLng, var open_now:Boolean) {
}