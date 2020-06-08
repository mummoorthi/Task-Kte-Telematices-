package com.example.moorthi.taskkttelematices.model

import android.util.Log
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
            realm.executeTransaction {
                it.where(LocationModel::class.java).equalTo("id", personId).findFirst()?.deleteFromRealm()
            }
        }

        fun insertUser(firstname: String, lastname: String, email: String, password: String) {
            val register = LoginModel()
            //location.id = System.currentTimeMillis()
            register.firstName = firstname
            register.lastName = lastname
            register.em = email
            register.password = password
            realm.executeTransactionAsync({
                it.insert(register)
            }, {
                Log.e("inserted", "successfully")
            }, {
                Log.e("inserted", "failed ${it.printStackTrace()}")
            })
        }
        fun getUser(): RealmResults<LoginModel> {
            return realm.where(LoginModel::class.java).findAll()
        }

         fun getSingleRecord(email: String, password: String): RealmResults<LoginModel?>? {
             if(email!=null && password!=null){
                 return realm.where<LoginModel>(LoginModel::class.java)
                     .equalTo("Em", email)
                     .equalTo("Password", password)
                     .findAll()
             }
           return null
        }
    }
    
}