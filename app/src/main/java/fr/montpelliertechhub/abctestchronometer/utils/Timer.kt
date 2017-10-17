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
        mTimeWhenPaused = sharedPreferences.getLong(KEY_TIME_PAUSED + mChronometer.getId(),
                mChronometer.getBase() - SystemClock.elapsedRealtime())
        //some negative value
        mChronometer.setBase(SystemClock.elapsedRealtime() + mTimeWhenPaused)
        mChronometer.stop()
        if (isHourFormat) {
            val text = mChronometer.getText()
            if (text.length == 5) {
                mChronometer.setText("00:" + text)
            } else if (text.length == 7) {
                mChronometer.setText("0" + text)
            }
        }
    }

    private fun storeState(state: ChronometerState) {
        sharedPreferences.edit().putInt(KEY_STATE + mChronometer.getId(), state.ordinal).apply()
    }

    fun startChronometer() {
        storeState(ChronometerState.Running)
        saveBase()
        startStateChronometer()
    }

    fun hourFormat(hourFormat: Boolean) {
        isHourFormat = hourFormat
        if (isHourFormat) {
            mChronometer.setOnChronometerTickListener({ c ->
                val elapsedMillis = SystemClock.elapsedRealtime() - c.base
                if (elapsedMillis > 3600000L) {
                    c.format = "0%s"

                } else {
                    c.format = "00:%s"
                }
            })
        } else {

            mChronometer.setOnChronometerTickListener(null)
            mChronometer.setFormat("%s")
        }
    }


    private fun startStateChronometer() {
        mTimeBase = sharedPreferences.getLong(KEY_BASE + mChronometer.getId(),
                SystemClock.elapsedRealtime()) //0
        mTimeWhenPaused = sharedPreferences.getLong(KEY_TIME_PAUSED + mChronometer.getId(), 0)
        mChronometer.setBase(mTimeBase + mTimeWhenPaused)
        mChronometer.start()
    }

    fun stopChronometer() {
        storeState(ChronometerState.Stopped)
        mChronometer.setBase(SystemClock.elapsedRealtime())
        mChronometer.stop()
        if (isHourFormat)
            mChronometer.setText("00:00:00")
        else
            mChronometer.setText("00:00")
        clearState()
    }

    private fun clearState() {
        storeState(ChronometerState.Stopped)
        sharedPreferences.edit()
                .remove(KEY_BASE + mChronometer.getId())
                .remove(KEY_TIME_PAUSED + mChronometer.getId())
                .apply()
        mTimeWhenPaused = 0
    }

    private fun saveBase() {
        sharedPreferences.edit()
                .putLong(KEY_BASE + mChronometer.getId(), SystemClock.elapsedRealtime())
                .apply()
    }

    private fun saveTimeWhenPaused() {
        sharedPreferences.edit()
                .putLong(KEY_TIME_PAUSED + mChronometer.getId(),
                        mChronometer.getBase() - SystemClock.elapsedRealtime())
                .apply()
    }

    fun resumeState() {
        val state = ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.getId(),
                ChronometerState.Stopped.ordinal)]
        if (state.ordinal == ChronometerState.Stopped.ordinal) {
            stopChronometer()
        } else if (state.ordinal == ChronometerState.Paused.ordinal) {
            pauseStateChronometer()
        } else {
            startStateChronometer()
        }
    }

    fun isRunning(): Boolean {
        return ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.getId(),
                ChronometerState.Stopped.ordinal)] == ChronometerState.Running
    }

    fun isPaused(): Boolean {
        return ChronometerState.values()[sharedPreferences.getInt(KEY_STATE + mChronometer.getId(),
                ChronometerState.Stopped.ordinal)] == ChronometerState.Paused
    }
}
