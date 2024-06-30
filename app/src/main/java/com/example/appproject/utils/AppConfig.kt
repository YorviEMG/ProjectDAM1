package com.example.appproject.utils

import android.app.Application
import android.content.Context
import com.example.appproject.data.InitBD

class AppConfig: Application() {
    companion object {
        lateinit var CONTEXT:Context
        lateinit var BD: InitBD
        var BD_NAME = "gamearena.bd"
        var VERSION = 1
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        BD = InitBD()
    }
}