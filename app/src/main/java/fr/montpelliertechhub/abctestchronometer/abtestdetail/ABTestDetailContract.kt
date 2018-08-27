package fr.montpelliertechhub.abctestchronometer.abtestdetail

import fr.montpelliertechhub.abctestchronometer.BasePresenter
import fr.montpelliertechhub.abctestchronometer.BaseView
import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.ABTest

/**
 * This specifies the contract between the view and the presenter.
 *
 * Created by Hugo Gresse on 15/08/2018.
 */
interface ABTestDetailContract {

    interface View : BaseView<Presenter> {
        var isActive: Boolean
        fun showResume(text: AB?)
        fun hideResume()
        fun showABs(abList: ABTest)
        fun showMissingABTest()
        fun showNoAB()
        fun showAddAB()
        fun showABMeasure(ab: AB)
    }

    interface Presenter : BasePresenter {
        fun editABTest()
        fun deleteABTest()
        fun openABTestMeasure(ab: AB)
        fun addNewAB()
    }

}