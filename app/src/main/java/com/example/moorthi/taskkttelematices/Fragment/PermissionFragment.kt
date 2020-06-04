package com.example.moorthi.taskkttelematices.Fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moorthi.taskkttelematices.MainActivity

import com.example.moorthi.taskkttelematices.R

/**
 * A simple [Fragment] subclass.
 */
class PermissionFragment : Fragment() {
    lateinit var mContext:MainActivity
    private var LOCATION_PERMISSION = 1;
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_permission, container, false)
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION
        )
        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mContext.loadUI()
            }else if (shouldShowRequestPermissionRationale(permissions[0])) {
                mContext.showMessage("Please grant location access permission")
            }
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
