package fr.montpelliertechhub.abctestchronometer.utils;

import android.content.SharedPreferences
import android.os.SystemClock
import android.widget.Chronometer


/**
 * A timer that measure stuff
 *
 * Created by Hugo Gresse on 19/09/2017.
 */

class Timer(val mChronometer: Chronometer, val sharedPreferences: SharedPreferences) {

    private val KEY_TIME_PAUSED = "TimePaused"
    private val KEY_BASE = "TimeBase"
    private val KEY_STATE = "ChronometerState"
    private var isHourFormat = false

    internal enum class ChronometerState {
        Running, Paused, Stopped
    }

    var mTimeWhenPaused: Long = 0
    var mTimeBase: Long = 0

    fun pauseChronometer() {
        storeState(ChronometerState.Paused)
        saveTimeWhenPaused()
        pauseStateChronometer()
    }

    private fun pauseStateChronometer() {
        mTimeWhenPaused = sharedPreferences.getLong(KEY_TIME_PAUSED + mChronometer.id,
                mChronometer.base - SystemClock.elapsedRealtime())
        //some negative value
        mChronometer.base = SystemClock.elapsedRealtime() + mTimeWhenPaused
        mChronometer.stop()
        if (isHourFormat) {
            val text = mChronometer.text
            if (text.length == 5) {
                mChronometer.text = "00:" + text
            } else if (text.length == 7) {
                mChronometer.text = "0" + text
            }
        }
    }

    private fun storeState(state: ChronometerState) {
        sharedPreferences.edit().putInt(KEY_STATE + mChronometer.getId(), state.ordinal).apply()
    }

    fun startChronometer(): Long {
        storeState(ChronometerState.Running)
        saveBase()
        return startStateChronometer()
    }

    fun hourFormat(hourFormat: Boolean) {
        isHourFormat = hourFormat
        if (isHourFormat) {
            mChronometer.setOnChronometerTickListener { c ->
                val elapsedMillis = SystemClock.elapsedRealtime() - c.base
                if (elapsedMillis > 3600000L) {
                    c.format = "0%s"

                } else {
                    c.format = "00:%s"
                }
            }
        } else {

            mChronometer.onChronometerTickListener = null
            mChronometer.format = "%s"
        }
    }


    private fun startStateChronometer(): Long {
        mTimeBase = sharedPreferences.getLong(KEY_BASE + mChronometer.id,
                SystemClock.elapsedRealtime()) //0
        mTimeWhenPaused = sharedPreferences.getLong(KEY_TIME_PAUSED + mChronometer.id, 0)
        val startTime = mTimeBase + mTimeWhenPaused
        mChronometer.base = startTime
        mChronometer.start()
        return startTime
    }

    fun stopChronometer() {
        storeState(ChronometerState.Stopped)
        mChronometer.base = SystemClock.elapsedRealtime()
        mChronometer.stop()
        if (isHourFormat)
            mChronometer.text = "00:00:00"
        else
            mChronometer.text = "00:00"
        clearState()
    }

    private fun clearState() {
        storeState(ChronometerState.Stopped)
        sharedPreferences.edit()
                .remove(KEY_BASE + mChronometer.id)
                .remove(KEY_TIME_PAUSED + mChronometer.id)
                .apply()
        mTimeWhenPaused = 0
    }

    private fun saveBase() {
        sharedPreferences.edit()
                .putLong(KEY_BASE + mChronometer.id, SystemClock.elapsedRealtime())
                .apply()
    }

    private fun saveTimeWhenPaused() {
        sharedPreferences.edit()
                .putLong(KEY_TIME_PAUSED + mChronometer.id,
                        mChronometer.base - SystemClock.elapsedRealtime())
                .apply()
    }

    fun resumeState() {
        val state = ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.id,
                ChronometerState.Stopped.ordinal)]
        when {
            state.ordinal == ChronometerState.Stopped.ordinal -> stopChronometer()
            state.ordinal == ChronometerState.Paused.ordinal -> pauseStateChronometer()
            else -> startStateChronometer()
        }
    }

    fun isRunning(): Boolean {
        return ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.id,
                ChronometerState.Stopped.ordinal)] == ChronometerState.Running
    }

    fun isPaused(): Boolean {
        return ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.id,
                ChronometerState.Stopped.ordinal)] == ChronometerState.Paused
    }
}
