package fr.montpelliertechhub.abctestchronometer.measure

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import fr.montpelliertechhub.abctestchronometer.ChronometerPersist
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository



class MeasureActivity : AppCompatActivity() {


    companion object {

        private const val INTENT_ABTESTCONTAINER_POS: String = "intent_abtestcontainer_position"
        private const val  INTENT_ABTEST_POS: String = "intent_abtest_position"

        fun onNewIntent(context: Context, containerPosition: Int, abTestPosition: Int): Intent {
            return Intent(context, MeasureActivity::class.java).apply {
                putExtra(INTENT_ABTESTCONTAINER_POS, containerPosition)
                putExtra(INTENT_ABTEST_POS, abTestPosition)
            }
        }
    }


    val mChronometerView by lazy { findViewById<Chronometer>(R.id.chronometerView) }
    val mStartButton by lazy { findViewById<Button>(R.id.startButton) }
    val mSaveButton by lazy { findViewById<Button>(R.id.saveButton) }
    val mSecondView by lazy { findViewById<SecondView>(R.id.secondView) }
    val mTitle by lazy { findViewById<TextView>(R.id.title) }

    lateinit var abTest : ABTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        val positionContainerPosition = intent.getIntExtra(INTENT_ABTESTCONTAINER_POS, -1)
        val abTestPosition = intent.getIntExtra(INTENT_ABTEST_POS, -1)

        val chronometerPersist: ChronometerPersist = ChronometerPersist(
                mChronometerView,
                getSharedPreferences("KeyAChanger", Context.MODE_PRIVATE))
         abTest = ABTestRepository.abTestContainer[positionContainerPosition].abtests[abTestPosition]

        mTitle.setText(abTest.title)

        chronometerPersist.resumeState()

        if(chronometerPersist.isRunning){
            chronometerPersist.startChronometer()
            mSecondView.start()
            mStartButton.setText("Pause")
        }

        mStartButton.setOnClickListener({
            if(chronometerPersist.isRunning){
                chronometerPersist.pauseChronometer()
                mStartButton.setText("Resume")
                mSecondView.pause()
            } else {
                mStartButton.setText("Pause")
                chronometerPersist.startChronometer()
                mSecondView.start()
            }
        })

    }
}