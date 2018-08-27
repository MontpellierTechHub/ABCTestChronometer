package fr.montpelliertechhub.abctestchronometer.measure

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import arrow.core.Some
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import fr.montpelliertechhub.abctestchronometer.repository.ABTestsDataSource
import fr.montpelliertechhub.abctestchronometer.utils.Timer
import kotlinx.android.synthetic.main.activity_measure.*


class MeasureActivity : AppCompatActivity() {

    companion object {

        private const val INTENT_AB_ID: String = "intent_ab_id"

        fun onNewIntent(context: Context?, abId: Long): Intent {
            return Intent(context, MeasureActivity::class.java).apply {
                putExtra(INTENT_AB_ID, abId)
            }
        }
    }

    lateinit var mAb: AB
    lateinit var mTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        val abId = intent.getLongExtra(INTENT_AB_ID, -1)

        ABTestRepository.getInstance(applicationContext).getAB(abId, object : ABTestsDataSource.GetABCallback {
            override fun onABLoaded(ab: AB) {
                onABReceive(ab)
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    private fun onABReceive(ab: AB) {
        mAb = ab

        titleTextView.text = getString(R.string.title_activity_measure, mAb.from, mAb.to)

        mTimer = Timer(timerChronometer, getSharedPreferences("ChronometerSample", MODE_PRIVATE))
        val baseTime = mTimer.resumeState()
        when (baseTime) {
            is Some -> resumeTimer(baseTime.t)
        }
        timerButton.setOnClickListener {
            if (mTimer.isRunning()) {
                pauseTimer()
            } else {
                resumeTimer(mTimer.startChronometer())
            }
        }
        stopButton.setOnClickListener {
            mTimer.stopChronometer()
            chronometerView.stop()
            timerButton.setText(R.string.action_resume)
            stopButton.visibility = View.GONE
        }
    }

    private fun pauseTimer() {
        timerButton.setText(R.string.action_resume)
        mTimer.pauseChronometer()
        chronometerView.pause()
    }

    private fun resumeTimer(baseTime: Long) {
        timerButton.setText(R.string.action_pause)
        chronometerView.start(baseTime)
        stopButton.visibility = View.VISIBLE
    }

}