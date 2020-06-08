package com.example.moorthi.taskkttelematices

import android.app.Application
import android.content.Intent
import io.realm.Realm
import io.realm.RealmConfiguration


class App : Application() {

    var mContext: Appsevice? = null
    override fun onCreate() {
            super.onCreate()
            Realm.init(applicationContext)
            val config = RealmConfiguration.Builder().name("demo.realm").build()
            Realm.setDefaultConfiguration(config)


        }


    }
