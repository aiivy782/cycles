package com.aiivy782.cycles.time

import android.content.Context
import com.aiivy782.cycles.R

data class ElapsedTime(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val secs: Int,
) {
    /**
     * Возвращает строку формата "2 days, 12:45:31"
     */
    fun humanReadableFormat(context: Context): String {
        return context.getString(R.string.timer_format, days, hours, minutes, secs)
    }

    companion object {
        /**
         * Разбивает elapsedTime на компоненты: секунды, минуты, часы
         */
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