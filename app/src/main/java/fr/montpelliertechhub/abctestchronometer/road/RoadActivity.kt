package fr.montpelliertechhub.abctestchronometer.road

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.measure.MeasureActivity
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import kotlinx.android.synthetic.main.activity_road.*


class RoadActivity : AppCompatActivity() {

    companion object {

        private const val INTENT_ABTESTCONTAINER_POS: String = "intent_abtestcontainer_position"

        fun onNewIntent(context: Context, position: Int): Intent {
            return Intent(context, RoadActivity::class.java).apply {
                putExtra(INTENT_ABTESTCONTAINER_POS, position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road)

        val containerPosition = intent.getIntExtra(INTENT_ABTESTCONTAINER_POS, -1)

        val abTests = ABTestRepository.getInstance(applicationContext).getABTests()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RoadAdapter(abTests[containerPosition]) {
            // We may use Anko here, see https://discuss.kotlinlang.org/t/java-interopt-android-intent/1450
            startActivity(MeasureActivity.onNewIntent(
                    this@RoadActivity,
                    containerPosition,
                    abTests[containerPosition].ab.indexOf(it)))
        }
    }
}