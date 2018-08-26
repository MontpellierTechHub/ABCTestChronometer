package fr.montpelliertechhub.abctestchronometer.abtestdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.montpelliertechhub.abctestchronometer.Injection
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.utils.replaceFragmentInActivity


class ABTestDetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_ABTEST_ID: String = "intent_abtest_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.abtestdetail_activity)

        val containerPosition = intent.getLongExtra(INTENT_ABTEST_ID, -1)

        val abTestsDetailFragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as ABTestDetailFragment?
                ?: ABTestDetailFragment.newInstance().also { replaceFragmentInActivity(it, R.id.contentFrame) }

        ABTestDetailPresenter(
                containerPosition,
                Injection.provideABTestsRepository(applicationContext),
                abTestsDetailFragment)
    }
}