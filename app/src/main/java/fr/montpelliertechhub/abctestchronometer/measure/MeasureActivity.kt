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
import fr.montpelliertechhub.abctestchronometer.utils.Timer
import kotlinx.android.synthetic.main.activity_measure.*


class MeasureActivity : AppCompatActivity() {

    companion object {

        private const val INTENT_ABTESTCONTAINER_POS: String = "intent_abtestcontainer_position"
        private const val INTENT_ABTEST_POS: String = "intent_abtest_position"

        fun onNewIntent(context: Context, containerPosition: Int, abTestPosition: Int): Intent {
            return Intent(context, MeasureActivity::class.java).apply {
                putExtra(INTENT_ABTESTCONTAINER_POS, containerPosition)
                putExtra(INTENT_ABTEST_POS, abTestPosition)
            }
        }
    }

    lateinit var mAb: AB
    lateinit var mTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        val positionContainerPosition = intent.getIntExtra(INTENT_ABTESTCONTAINER_POS, -1)
        val abTestPosition = intent.getIntExtra(INTENT_ABTEST_POS, -1)

        mAb = ABTestRepository.getInstance(applicationContext).getABTests()[positionContainerPosition].ab[abTestPosition]

        titleTextView.text = getString(R.string.title_activity_measure, mAb.from, mAb.to)

        mTimer = Timer(timerChronometer, getSharedPreferences("ChronometerSample", MODE_PRIVATE))
        val baseTime = mTimer.resumeState()
        when(baseTime) {
            is Some -> resumeTimer(baseTime.t)
        }
        timerButton.setOnClickListener {
            if (mTimer.isRunning()) {
                pauseTimer()
            } else {
                resumeTimer(mTimer.startChronometer())
            }
        }
        stopButton.setOnClickListener{
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