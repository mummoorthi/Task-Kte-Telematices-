package com.example.moorthi.taskkttelematices.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
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


class MapFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var mContext: MainActivity
    private var mMap: GoogleMap? = null

    var mLocationManager: LocationManager? = null
    var latitude: Double? = null
    var longitude: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragemnt_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val bundle = arguments
        if (bundle != null) {
            latitude = arguments!!.getDouble("latitude")
            longitude = arguments!!.getDouble("logitude")

        } else {
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
        Log.e("map", "Ready")
        if (latitude != null && longitude != null) {
            val locationlat = LatLng(latitude!!, longitude!!)
            val marker =    MarkerOptions().position(locationlat).title("Location")
                .snippet("Latittude: " + latitude + "  Longitude: " + longitude)

            mMap!!.addMarker(
                marker
            ).showInfoWindow()

            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(locationlat))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }


    @SuppressLint("MissingPermission")
    private fun loadLocation() {
        if (mLocationManager == null) {
            mLocationManager =
                mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            mLocationManager?.requestLocationUpdates(0, 0f, getCriteria(), this, null)
        }
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
        Log.e("onLocationChanged1", "called")
        if (p0 != null) {
            longitude = p0.longitude
            latitude=p0.latitude
            if (mMap!=null) {
                val locationlat = LatLng(p0.latitude, p0.longitude)
                mMap!!.addMarker(
                    MarkerOptions().position(locationlat).title("Location")
                        .snippet("Latitude: " + p0.latitude + "  Longitude: " + p0.longitude)
                        .draggable(true)
                ).showInfoWindow()
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(locationlat))
            }
            mLocationManager?.removeUpdates(this)
            mLocationManager=null
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationManager?.removeUpdates(this)
        mLocationManager=null
    }




}




