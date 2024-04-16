package com.aiivy782.cycles

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder

data class ElapsedTime(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val secs: Int,
) {
    companion object {
        fun parse(elapsedTime: Long): ElapsedTime {
            val days = elapsedTime / (1000 * 60 * 60 * 24)
            val hours = (elapsedTime / (1000 * 60 * 60)) % 24
            val minutes = (elapsedTime / (1000 * 60)) % 60
            val secs = (elapsedTime / 1000) % 60
            return ElapsedTime(
                days = days.toInt(),
                hours = hours.toInt(),
                minutes = minutes.toInt(),
                secs = secs.toInt()
            )
        }
    }
}

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_START_TIME = "start_time"
        const val DELAY_MILLIS = 100L
    }

    private lateinit var timerTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var resetButton: Button

    private val preferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    private val handler = Handler(Looper.getMainLooper())

    private var startTime: Long
        get() = preferences.getLong(KEY_START_TIME, 0L)
        set(value) = preferences.edit().putLong(KEY_START_TIME, value).apply()

    private val timerTask = object : Runnable {
        @SuppressLint("StringFormatInvalid")
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeSinceStart = currentTime - startTime
            val elapsedDays = ElapsedTime.parse(elapsedTimeSinceStart).days

            // Calculate the time elapsed since the start of the current day
            val currentDayStartTime = startTime + (elapsedDays * 24 * 60 * 60 * 1000L)
            val elapsedTimeSinceCurrentDayStart = currentTime - currentDayStartTime

            // Calculate the progress as a ratio of elapsed time since current day start to the total time in a day (24 hours), and map to the range 0-100
            val progress = (elapsedTimeSinceCurrentDayStart.toFloat() / (24 * 60 * 60 * 1000L)) * 1000

            ElapsedTime.parse(elapsedTimeSinceStart).let {
                timerTextView.text = getString(R.string.timer_format, it.days, it.hours, it.minutes, it.secs)
                progressBar.progress = progress.toInt()
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
