package com.example.moorthi.taskkttelematices

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
        override fun onCreate() {
            super.onCreate()
            Realm.init(applicationContext)
            val config = RealmConfiguration.Builder().name("demo.realm").build()
            Realm.setDefaultConfiguration(config)

        }
    }
