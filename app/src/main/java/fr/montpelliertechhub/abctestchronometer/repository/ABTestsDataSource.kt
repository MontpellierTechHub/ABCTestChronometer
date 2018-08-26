package fr.montpelliertechhub.abctestchronometer.repository

import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.ABTest

/**
 * Created by Hugo Gresse on 22/08/2018.
 */
interface ABTestsDataSource {

    interface LoadABTestsCallback {
        fun onABTestsLoaded(abTests: List<ABTest>)
        fun onDataNotAvailable()
    }

    interface GetABTestCallback {
        fun onABTestsLoaded(abTest: ABTest)
        fun onDataNotAvailable()
    }

    interface GetABCallback {
        fun onABLoaded(ab: AB)
        fun onDataNotAvailable()
    }
}