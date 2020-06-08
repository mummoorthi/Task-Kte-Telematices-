package com.example.moorthi.taskkttelematices

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.example.moorthi.taskkttelematices.Fragment.PermissionFragment
import com.example.moorthi.taskkttelematices.model.Controller


class Appsevice : JobIntentService(),LocationListener{

    val mHandler = Handler()
    private val TAG = "Appsevice"
    private val JOB_ID = 2
    var mLocationManager: LocationManager? = null
    private var LOCATION_PERMISSION = 1;
val mContext: MainActivity? = null
    fun enqueueWork(context: Context?, intent: Intent?) {
        enqueueWork(context!!, Appsevice::class.java, JOB_ID, intent!!)
    }

    override fun onCreate() {
        super.onCreate()
        showNotification()
       checkPermission()
    }
    fun checkPermission() {
        mContext?.loadPermissionUI()
    }

    override fun onHandleWork(intent: Intent) {
        val maxCount = intent.getIntExtra("maxCountValue", -1)
        for (i in 0 until maxCount) {
            if (applicationContext.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED) {
                loadLocation()
            }
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun showNotification() {
        val NOTIFICATION_CHANNEL_ID = "com.example.moorthi.taskkttelematices"
        val channelName = "Location update"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            manager.createNotificationChannel(chan)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Location data...")
            .setTicker("AOOOOOO")
            .setContentText("Update location")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setSmallIcon(R.drawable.ic_menu_mylocation)
            .build()
            startForeground(2, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    @SuppressLint("MissingPermission")
    fun loadLocation() {
        if (mLocationManager == null) {
            mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                    mLocationManager?.requestLocationUpdates(900000, 0f, getCriteria(), this, null)

            }, 100)
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
        }    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }


}



