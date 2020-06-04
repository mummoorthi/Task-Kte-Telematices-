package com.example.moorthi.taskkttelematices.Database

import android.location.Location
import android.text.Editable
import android.util.Log
import com.example.moorthi.taskkttelematices.model.LocationModel
import io.realm.Realm
import io.realm.RealmResults

class Realm {
    companion object {
        val realm = Realm.getDefaultInstance()
      //  fun insertLocation(p0: Location) {
            fun insertLocation(lat: Double, lng: Double) {
                val location = LocationModel()
            location.id=System.currentTimeMillis()
            location.lat=lat
            location.lng=lng
            realm.executeTransactionAsync ({
                it.insert(location)
            },{
                Log.e("inserted","successfully")
            },{
                Log.e("inserted","failed")
            })
        }

        fun getLocations(): RealmResults<LocationModel> {
            return realm.where(LocationModel::class.java).findAll()
        }

        fun delete(personId: Long){
            realm.executeTransaction { r ->
                val person: LocationModel? =
                    r.where(LocationModel::class.java).equalTo("id", personId).findFirst()
                if (person != null) {
                    person.deleteFromRealm()
                }
            }
        }
    }


}