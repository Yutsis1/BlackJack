package com.example.test

import android.app.Service
import android.content.Intent
import android.os.IBinder

class DBService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
