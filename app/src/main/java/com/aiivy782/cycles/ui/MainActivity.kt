package com.aiivy782.cycles.ui

import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aiivy782.cycles.MyApplication
import com.aiivy782.cycles.R
import com.aiivy782.cycles.time.ElapsedTime
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_START_TIME = "start_time"
        const val DELAY_MILLIS = 100L
    }

    private lateinit var timerTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var resetButton: Button

    private val handler = Handler(Looper.getMainLooper())

    private var startTime: Long
        get() = MyApplication.preferences.getLong(KEY_START_TIME, 0L)
        set(value) = MyApplication.preferences.edit().putLong(KEY_START_TIME, value).apply()

    private val timerTask = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeSinceStart = currentTime - startTime

            ElapsedTime.parse(elapsedTimeSinceStart).let {
                timerTextView.text = it.humanReadableFormat(this@MainActivity)
                progressBar.progress = it.hours
            }

            handler.postDelayed(this, DELAY_MILLIS)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        DynamicColors.applyToActivitiesIfAvailable(Application())

        timerTextView = findViewById(R.id.timerTextView)
        progressBar = findViewById(R.id.progressBar)
        resetButton = findViewById(R.id.resetButton)

        resetButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Reset timer")
                .setMessage("Are you sure you want to reset the timer?")
                .setPositiveButton("Yes") { _, _ ->
                    resetTimer()
                }
                .setNegativeButton("No", null)
                .show()
        }

        if (startTime == 0L) {
            resetTimer()
        } else {
            startTimer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerTask)
    }

    private fun resetTimer() {
        val currentTime = System.currentTimeMillis()
        startTime = currentTime

        startTimer()
    }

    private fun startTimer() {
        timerTask.run()
    }
}
