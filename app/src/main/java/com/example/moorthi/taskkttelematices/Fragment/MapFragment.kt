package com.example.moorthi.taskkttelematices.Fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.moorthi.taskkttelematices.MainActivity
import com.example.moorthi.taskkttelematices.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout


class MapFragment: Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var mContext: MainActivity
    private var mMap: GoogleMap? = null
    val PERMISSION_ID = 42
    private var LOCATION_PERMISSION = 1;
    var mLocationManager: LocationManager? = null
    var latitude :Double? =null
    var longitude :Double? =null
    var markerName: Marker? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragemnt_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val bundle = arguments
        if (bundle != null) {
            latitude = arguments!!.getDouble("latitude")
            longitude = arguments!!.getDouble("logitude")
            print("SHOWVALUE " + latitude+" "+longitude)

        }else{
            loadLocation()
        }
        var fab = view.findViewById(R.id.fabmap) as FloatingActionButton

        fab.setOnClickListener { view ->
            val tabs =
                (activity as MainActivity?)!!.findViewById<View>(R.id.tabs) as TabLayout
               tabs.getTabAt(1)!!.select()

        }
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (latitude!=null && longitude!= null){
            val sydney = LatLng(latitude!!, longitude!!)
            markerName = mMap!!.addMarker(MarkerOptions().position(sydney).title("Race Start").snippet("Latitdude "+latitude+"  Longidude "+longitude).draggable(true))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }


    private fun loadLocation() {
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mLocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationManager?.requestLocationUpdates(0, 0f, getCriteria(), this, null)

    }

    private fun getCriteria(): Criteria {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.isAltitudeRequired = false
        criteria.isSpeedRequired = true
        criteria.isCostAllowed = true
        criteria.isBearingRequired = false
        return criteria
    }

    override fun onLocationChanged(p0: Location?) {
        print("CHECKCONTENT")
        if (p0 != null) {
            val sydney = LatLng(p0.latitude, p0.longitude)
            markerName = mMap!!.addMarker(MarkerOptions().position(sydney).title("Race Start").snippet("Latitdude "+p0.latitude+"  Longidude "+p0.longitude).draggable(true))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mLocationManager?.removeUpdates(this)
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationManager?.removeUpdates(this)
    }



}




