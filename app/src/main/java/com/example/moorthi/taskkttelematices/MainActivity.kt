package com.example.moorthi.taskkttelematices


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.moorthi.taskkttelematices.Adapter.MypagerAdapter
import com.example.moorthi.taskkttelematices.Fragment.ListFragment
import com.example.moorthi.taskkttelematices.Fragment.MapFragment
import com.example.moorthi.taskkttelematices.Fragment.PermissionFragment
import com.example.moorthi.taskkttelematices.model.Controller
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LocationListener {

    var mLocationManager: LocationManager? = null
    var adapter: MypagerAdapter? = null
    var mContext: Appsevice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MypagerAdapter(supportFragmentManager)
        checkPermission()
       // PermissionFragment()
        mContext =   Appsevice()
        val mIntent = Intent(this@MainActivity, Appsevice::class.java)
        mIntent.putExtra("maxCountValue", 1000)
        mContext?.enqueueWork(this@MainActivity, mIntent)
    }

    fun checkPermission() {
        loadPermissionUI()
    }

    fun loadUI() {
        adapter?.removeAllFragment()
        adapter?.addFragment(MapFragment(), "Map")
        adapter?.addFragment(ListFragment(), "List Location")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        loadLocation()
        tabs.visibility = View.VISIBLE
    }

    fun loadPermissionUI() {
        adapter?.removeAllFragment()
        adapter?.addFragment(PermissionFragment(), "Permission")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        tabs.visibility = View.GONE
    }


    fun showMessage(message: String) {
        Snackbar.make(objParent, message, Snackbar.LENGTH_LONG).show()
    }

    @SuppressLint("MissingPermission")
    fun loadLocation() {
        if (mLocationManager == null) {
            mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            showMessage("Starting gps")
            mLocationManager?.requestLocationUpdates(900000, 0f, getCriteria(), this, null)
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
        if (p0 != null) {
            Controller.insertLocation(p0.latitude, p0.longitude)
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
        mLocationManager = null
    }
}

