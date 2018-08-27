package fr.montpelliertechhub.abctestchronometer.abtests

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.montpelliertechhub.abctestchronometer.Injection
import fr.montpelliertechhub.abctestchronometer.R
import fr.montpelliertechhub.abctestchronometer.utils.replaceFragmentInActivity
import kotlinx.android.synthetic.main.abtests_activity.*


/**
 * Display the list of ABTests with add button.
 */
class ABTestsActivity : AppCompatActivity() {

    private lateinit var abTestsPresenter: ABTestsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.abtests_activity)
        setSupportActionBar(toolbar)

        val abTestsFragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as ABTestsFragment?
                ?: ABTestsFragment.newInstance().also { replaceFragmentInActivity(it, R.id.contentFrame) }

        abTestsPresenter = ABTestsPresenter(Injection.provideABTestsRepository(applicationContext), abTestsFragment)

        // TODO: implement load previously saved state, see https://github.com/googlesamples/android-architecture/blob/todo-mvp-kotlin/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/tasks/TasksActivity.kt#L66
    }
}
