package com.example.moorthi.taskkttelematices


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.moorthi.taskkttelematices.Adapter.MypagerAdapter
import com.example.moorthi.taskkttelematices.Database.Realm
import com.example.moorthi.taskkttelematices.Fragment.ListFragment
import com.example.moorthi.taskkttelematices.Fragment.MapFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LocationListener {
    private var LOCATION_PERMISSION = 1;
    var mLocationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION
            )
        }

        //  setSupportActionBar(toolbar)

        val adapter = MypagerAdapter(supportFragmentManager)
        adapter.addFragment(MapFragment(), "Map")
        adapter.addFragment(ListFragment(), "List Location")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[0])) {
                showMessage("Please grant location access permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun showMessage(message: String) {
        Snackbar.make(objParent, message, Snackbar.LENGTH_LONG).show()
    }

    fun loadLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        showMessage("Starting gps")
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
        if (p0!=null){
            Realm.insertLocation(p0.latitude,p0.longitude)

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
    }
}

