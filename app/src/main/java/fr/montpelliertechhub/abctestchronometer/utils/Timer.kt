package fr.montpelliertechhub.abctestchronometer.utils;

import android.content.SharedPreferences
import android.os.SystemClock
import android.widget.Chronometer
import arrow.core.None
import arrow.core.Option
import arrow.core.Some


/**
 * A timer that measure stuff
 *
 * Created by Hugo Gresse on 19/09/2017.
 */

private const val KEY_TIME_BASE = "TimeBase"
private const val KEY_TIME_PAUSED = "TimePaused"
private const val KEY_STATE = "ChronometerState"

class Timer(private val mChronometer: Chronometer, private val sharedPreferences: SharedPreferences) {

    internal enum class ChronometerState {
        Running, Paused, Stopped
    }

    private var isHourFormat = false
    private var mTimeWhenPaused: Long = 0
    private var mTimeBase: Long = 0

    fun resumeState(): Option<Long> {
        val state = ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.id,
                                                                       ChronometerState.Stopped.ordinal)]
        return when {
            state.ordinal == ChronometerState.Stopped.ordinal -> {
                stopChronometer()
                None
            }
            state.ordinal == ChronometerState.Paused.ordinal  -> {
                pauseStateChronometer()
                None
            }
            else                                              -> Some(startStateChronometer())
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

    fun pauseChronometer() {
        storeState(ChronometerState.Paused)
        saveTimeWhenPaused()
        pauseStateChronometer()
    }

    fun startChronometer(): Long {
        storeState(ChronometerState.Running)
        saveTimeBase()
        return startStateChronometer()
    }

    fun stopChronometer(): Long {
        val elapsedTime = SystemClock.elapsedRealtime()
        mChronometer.base = SystemClock.elapsedRealtime()
        mChronometer.stop()
        if (isHourFormat)
            mChronometer.text = "00:00:00"
        else
            mChronometer.text = "00:00"
        clearState()
        return elapsedTime
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
        mTimeBase = sharedPreferences.getLong(KEY_TIME_BASE + mChronometer.id, SystemClock.elapsedRealtime()) //0
        mTimeWhenPaused = sharedPreferences.getLong(KEY_TIME_PAUSED + mChronometer.id, 0)
        mChronometer.base = mTimeBase + mTimeWhenPaused
        mChronometer.start()
        return mTimeBase
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

    private fun clearState() {
        storeState(ChronometerState.Stopped)
        sharedPreferences.edit()
                .remove(KEY_TIME_BASE + mChronometer.id)
                .remove(KEY_TIME_PAUSED + mChronometer.id)
                .apply()
        mTimeWhenPaused = 0
    }

    private fun storeState(state: ChronometerState) {
        sharedPreferences.edit().putInt(KEY_STATE + mChronometer.id, state.ordinal).apply()
    }

    private fun saveTimeBase() {
        sharedPreferences.edit()
                .putLong(KEY_TIME_BASE + mChronometer.id, SystemClock.elapsedRealtime())
                .apply()
    }

    private fun saveTimeWhenPaused() {
        sharedPreferences.edit()
                .putLong(KEY_TIME_PAUSED + mChronometer.id,
                         mChronometer.base - SystemClock.elapsedRealtime())
                .apply()
    }
}
