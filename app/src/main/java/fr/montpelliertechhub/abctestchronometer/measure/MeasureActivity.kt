package fr.montpelliertechhub.abctestchronometer.measure

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Chronometer
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.utils.ChronometerView
import fr.montpelliertechhub.abctestchronometer.utils.Timer


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

    val mTimerChronometer by lazy { findViewById<Chronometer>(R.id.timerTextView) }
    val mTimerButton by lazy { findViewById<Button>(R.id.timerButton) }
    val mSecondView by lazy { findViewById<ChronometerView>(R.id.secondView) }


    lateinit var mAbTest: ABTest
    lateinit var mTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        val positionContainerPosition = intent.getIntExtra(INTENT_ABTESTCONTAINER_POS, -1)
        val abTestPosition = intent.getIntExtra(INTENT_ABTEST_POS, -1)

        // mAbTest = ABTestRepository.abTestContainer[positionContainerPosition].abtests[abTestPosition]

        mTimer = Timer(mTimerChronometer, getSharedPreferences("ChronometerSample", MODE_PRIVATE))
        mTimer.resumeState()
        if(mTimer.isRunning()){

            resumeTimer()
        }
        mTimerButton.setOnClickListener {
            if(mTimer.isRunning()){
                pauseTimer()
            } else {
                resumeTimer()
            }
        }
    }

    fun pauseTimer(){
        mTimerButton.text = "Resume"
        mTimer.pauseChronometer()
        mSecondView.pause()
    }

    fun resumeTimer(){
        mTimerButton.text = "Pause"
        mSecondView.start(mTimer.startChronometer())
    }

}