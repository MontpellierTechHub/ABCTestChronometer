package fr.montpelliertechhub.abctestchronometer.abtests

import fr.montpelliertechhub.abctestchronometer.BasePresenter
import fr.montpelliertechhub.abctestchronometer.BaseView
import fr.montpelliertechhub.abctestchronometer.models.ABTest

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by Hugo Gresse on 15/08/2018.
 */
interface ABTestsContract {

    interface View: BaseView<Presenter> {
        var isActive: Boolean
        fun showABTests(abtests: List<ABTest>)
        fun showNoABTests()
        fun showAddABTest()
        fun showABTestDetails(abTest: ABTest)
    }

    interface  Presenter: BasePresenter {
        fun loadABTest(forceUpdate: Boolean)
        fun addNewABTest()
        fun openABTest(abTest: ABTest)
    }

}