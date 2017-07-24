package fr.montpelliertechhub.abctestchronometer.road

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import fr.montpelliertechhub.abctestchronometer.utils.bindView



class RoadActivity : AppCompatActivity() {

    var INTENT_ABTESTCONTAINER_POS: String = "intent_abtestcontainer_position"

    val mRecyclerview by this.bindView<RecyclerView>(R.id.recyclerView)

    companion object {
        fun onNewIntent(context: Context, position: Int): Intent {
            return Intent(context, RoadActivity::class.java).apply {
                putExtra(RoadActivity::INTENT_ABTESTCONTAINER_POS.toString(), position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road)

        mRecyclerview.layoutManager = LinearLayoutManager(this)
        mRecyclerview.adapter = RoadAdapter(ABTestRepository.abTestContainer[0])
    }
}