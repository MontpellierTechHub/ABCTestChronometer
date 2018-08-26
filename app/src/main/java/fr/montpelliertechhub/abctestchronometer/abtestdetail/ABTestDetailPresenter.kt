package fr.montpelliertechhub.abctestchronometer.abtestdetail

import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository
import fr.montpelliertechhub.abctestchronometer.repository.ABTestsDataSource

/**
 * Listens to user actions from the UI ([TaskDetailFragment]), retrieves the data and updates
 * the UI as required.
 *
 * Created by Hugo Gresse on 23/08/2018.
 */
class ABTestDetailPresenter(private val abTestId: Long?,
                            private val abTestRepository: ABTestRepository,
                            private val abTestDetailView: ABTestDetailContract.View
) : ABTestDetailContract.Presenter {

    init {
        abTestDetailView.presenter = this
    }

    override fun start() {
        if(abTestId == null){
            abTestDetailView.showMissingABTest()
            return
        }
        abTestRepository.getABTest(abTestId, object: ABTestsDataSource.GetABTestCallback {
            override fun onABTestsLoaded(abTest: ABTest) {
                with(abTestDetailView) {

                    // The view may not be able to handle UI updates anymore
                    if(!isActive) {
                        return@onABTestsLoaded
                    }
                }
                showABTest(abTest)
            }

            override fun onDataNotAvailable() {
                with(abTestDetailView) {

                    // The view may not be able to handle UI updates anymore
                    if(!isActive) {
                        return@onDataNotAvailable
                    }
                    showMissingABTest()
                }
            }
        })
    }

    override fun editABTest() {
        if(abTestId == null) {
            abTestDetailView.showMissingABTest()
            return
        }
    }

    override fun deleteABTest() {
        if(abTestId == null) {
            abTestDetailView.showMissingABTest()
            return
        }
        abTestRepository.deleteABTest(abTestId)
        start()
    }

    override fun openABTestMeasure(ab: AB) {
        if(abTestId == null) {
            abTestDetailView.showMissingABTest()
            return
        }
        abTestDetailView.showABMeasure(ab)
    }

    override fun addNewAB() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showABTest(abTest: ABTest) {
        with(abTestDetailView) {
            if(abTestId == null){
                hideResume()
            } else {
                showResume(abTest.getBestWay())
                showABs(abTest)
            }
        }
    }

}