package fr.montpelliertechhub.abctestchronometer.measure

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository



class MeasureActivity : AppCompatActivity() {

    lateinit var abTest : ABTest

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        val positionContainerPosition = intent.getIntExtra(INTENT_ABTESTCONTAINER_POS, -1)
        val abTestPosition = intent.getIntExtra(INTENT_ABTEST_POS, -1)

        abTest = ABTestRepository.abTestContainer[positionContainerPosition].abtests[abTestPosition]
    }
}