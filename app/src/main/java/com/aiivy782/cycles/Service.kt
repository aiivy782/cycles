package com.aiivy782.cycles

import android.app.Service
import android.content.Intent
import android.os.IBinder

class Service : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
