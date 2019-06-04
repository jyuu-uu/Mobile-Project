package com.example.teamproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.gms.maps.MapView
import android.widget.Toast
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.koushikdutta.ion.Ion
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream


class MapsActivity() : OnMapReadyCallback, Fragment(),GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    lateinit var locationManager: LocationManager

    lateinit var mapView:MapView
    lateinit var country:LatLng
    lateinit var mylocation:Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //this.onInflate(MapsMadeActivity, null, savedInstanceState)
        val layout = inflater.inflate(R.layout.activity_maps, container, false)

        mapView = layout.findViewById(R.id.map) as MapView
        mapView.getMapAsync(this)

        return layout

//        return inflater.inflate(R.layout.activity_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mapView != null) {
            mapView.onCreate(savedInstanceState)
        }
        init()
    }
    private lateinit var mMap: GoogleMap
    fun init(){
        val mapFragment = AppCompatActivity().supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        readFile()
    }



    lateinit var keySet: ArrayList<String>
    var place_info: HashMap<String, Place_info> = HashMap()
    fun readFile() {
        val str_key = getString(R.string.google_api_key)
        Ion.with( this ).load( "https://maps.googleapis.com/maps/api/place/textsearch/xml?query=restaurants+in+Sydney&key="+str_key)
            .asInputStream()
            .setCallback { e, result ->
                if(result!=null) {
                    parsingXML(result)
                }
                //else
                //Toast.makeText(applicationContext, "뉴스를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    fun parsingXML( result:InputStream ) {
        var factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware=true
        val xpp = factory.newPullParser()
        xpp.setInput(result, "utf-8")
        var eventType = xpp.eventType

        var isItem = false
        var dataSet = false
        var status = 0
        var i=0

        while (eventType != XmlPullParser.END_DOCUMENT) {
            var name =""
            var formatted_address=""
            var location=""
            var open_now = false
            when(eventType){
                XmlPullParser.START_DOCUMENT->{}

                XmlPullParser.START_TAG -> {
                    val tagname = xpp.name
                    if(isItem){
                        if(tagname.equals("title")||tagname.equals("link")||tagname.equals("description")) {
                            dataSet = true
                            when (tagname) {
                                "name" -> {
                                    status = 1
                                }
                                "formatted_address" -> {
                                    status = 2
                                }
                                "location" -> {
                                    status = 3
                                }
                                "open_now"->{
                                    status = 4
                                }
                            }
                        }
                    }
                    if (tagname.equals("result")) {
                        isItem = true
                    }
                }

                XmlPullParser.TEXT->{
                    if ( dataSet ) {
                        if ( status == 1 ) {
                            name = xpp .text
                            place_info.put( name , Place_info( name , "" , "" , false))
                            keySet.add (name)
                        } else if ( status == 2 ) {
                            formatted_address = xpp.text
                            place_info.get( keySet.get(i))!!.formatted_address=formatted_address
                        } else if ( status == 3 ) {
                            location = xpp.text
                            place_info.get(keySet.get(i))!!.location= location
                        } else if(status == 4){
                            if(xpp.text.equals("true")||xpp.text.equals("TRUE"))
                                open_now = true
                            else if(xpp.text.equals("false")||xpp.text.equals("FALSE"))
                                open_now = false
                            place_info.get(keySet.get(i))!!.open_now = open_now
                            status = 0
                            i ++
                            isItem = false
                        }
                        dataSet = false ;
                    }
                }
                XmlPullParser.END_TAG->{
                }
            }
            eventType = xpp.next()
        }
    }

    fun onbtnClick(p0:GoogleMap?){
        btnfindcountry.setOnClickListener {
            Log.v("imin", "imin")
            if(!findcountry.text.toString().equals("")){
                var str = findcountry.text.toString().split(",")
                if(str[1].toCharArray()[0].equals(" ")) {
                    val str_second = str[1].toCharArray()
                    var str_result = ""
                    for(i in str_second.indices){
                        if(i != 0)
                            str_result += str_result[i]
                    }
                    var str_first:List<String> = emptyList()
                    str_first.contains(str[0])
                    str_first.contains(str_result)
                    str = str_first
                }
                country = LatLng(str[0].toDouble(), str[1].toDouble())
            }else{
                country = LatLng(-33.852, 151.211)
            }
            this.onMapReady(p0)
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        //맵에 마커 넣기
        mMap = p0!!

        mMap.setMyLocationEnabled(true)
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        locationManager = this.activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location_recently = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            0, 0.1f, gpsLocationListener())
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
            0, 0.1f, gpsLocationListener())

        country = LatLng(10.0, 10.0)
        mylocation = Location("")
        gpsLocationListener().onLocationChanged(location_recently)
        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(country).title("Here You find"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(country))

        //내 위치 받기
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.

        onbtnClick(p0)
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    inner class gpsLocationListener():LocationListener {
        override fun onLocationChanged(loc:Location) {
            var provider = loc.getProvider();
            mylocation.longitude = loc.getLongitude();
            mylocation.latitude = loc.getLatitude();
            var altitude = loc.getAltitude();
            country = LatLng(mylocation.latitude, mylocation.longitude)
        }

        override fun onStatusChanged(provider:String, status:Int, extras:Bundle) {

        }
        override fun onProviderEnabled(provider:String) {

        }
        override fun onProviderDisabled(provider:String) {

        }
    }

    override fun onMyLocationClick(location: Location) {
        //Toast.makeText(this, "Current location:"+location, Toast.LENGTH_LONG).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onStart() {
        super.onStart();
        mapView.onStart();
    }

    override fun onStop() {
        super.onStop();
        mapView.onStop();
    }

    override fun onSaveInstanceState(outState:Bundle) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    override fun onResume() {
        super.onResume();
        mapView.onResume();
    }

    override fun onPause() {
        super.onPause();
        mapView.onPause();
    }

    override fun onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    override fun onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
}