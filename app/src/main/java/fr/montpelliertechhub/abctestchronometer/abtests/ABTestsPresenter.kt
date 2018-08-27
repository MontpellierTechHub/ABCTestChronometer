package fr.montpelliertechhub.abctestchronometer.abtests

import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import fr.montpelliertechhub.abctestchronometer.repository.ABTestsDataSource
import fr.montpelliertechhub.abctestchronometer.utils.EspressoIdlingResource

/**
 * Created by Hugo Gresse on 21/08/2018.
 */

class ABTestsPresenter(val abTestsRepository: ABTestRepository,
                       val abTestsView: ABTestsContract.View) : ABTestsContract.Presenter {

    init {
        abTestsView.presenter = this
    }

    override fun start() {
        loadABTest(false)
    }

    override fun loadABTest(forceUpdate: Boolean) {
        if (forceUpdate) {
            // TODO : refresh data on repo
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment() // App is busy until further notice

        abTestsRepository.getABTests(object : ABTestsDataSource.LoadABTestsCallback {
            override fun onABTestsLoaded(abTests: List<ABTest>) {
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
                    EspressoIdlingResource.decrement() // Set app as idle.
                }

                if (!abTestsView.isActive) {
                    return
                }

                processABTests(abTests)
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun addNewABTest() {
        abTestsView.showAddABTest()
    }

    override fun openABTest(abTest: ABTest) {
        abTestsView.showABTestDetails(abTest)
    }

    private fun processABTests(abTests: List<ABTest>) {
        if (abTests.isEmpty()) {
            processEmptyTasks()
        } else {
            abTestsView.showABTests(abTests)
        }
    }

    private fun processEmptyTasks() {
        abTestsView.showNoABTests()
    }
}