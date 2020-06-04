package com.example.moorthi.taskkttelematices

import android.util.Log
import com.example.moorthi.taskkttelematices.model.LocationModel
import io.realm.Realm
import io.realm.RealmResults

class Controller {
    companion object {
        private val realm = Realm.getDefaultInstance()


        fun insertLocation(lat: Double, lng: Double) {
            val location = LocationModel()
            location.id = System.currentTimeMillis()
            location.lat = lat
            location.lng = lng
            realm.executeTransactionAsync({
                it.insert(location)
            }, {
                Log.e("inserted", "successfully")
            }, {
                Log.e("inserted", "failed ${it.printStackTrace()}")
            })
        }

        fun getLocations(): RealmResults<LocationModel> {
            return realm.where(LocationModel::class.java).findAll()
        }

        fun delete(personId: Long) {
            realm.executeTransaction { it ->
                it.where(LocationModel::class.java).equalTo("id", personId).findFirst()?.deleteFromRealm()
            }
        }
    }


}