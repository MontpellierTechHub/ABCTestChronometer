package fr.montpelliertechhub.abctestchronometer.repository

import android.content.Context
import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.models.MyObjectBox
import fr.montpelliertechhub.abctestchronometer.models.Try
import fr.montpelliertechhub.abctestchronometer.utils.SingletonHolder
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class ABTestRepository private constructor(context: Context) {

    private val boxStore: BoxStore = MyObjectBox.builder().androidContext(context).build()
    private val mAbTestBox: Box<ABTest>
    private val mAbBox: Box<AB>

    init {
        mAbTestBox = boxStore.boxFor()
        mAbBox = boxStore.boxFor()

        if(mAbTestBox.count() == 0L){
            mAbTestBox.put(ABTest(
                    title = "MAUG",
                    abList = listOf(
                            AB(
                                    0,
                                    "Depuis le lointain",
                                    "Baillargues",
                                    "Montpellier",
                                    tryList = listOf()
                            ),
                            AB(
                                    0,
                                    "Depuis moins le lointain",
                                    "Castelnau",
                                    "Montpellier",
                                    tryList = listOf(
                                            Try(value = 25.0, date = "Hier")
                                    )
                            )
                    )
            ))

            mAbTestBox.put(ABTest(
                    title = "GOT",
                    abList = listOf(
                            AB(
                                    0,
                                    "De l'autre côté",
                                    "Bravoos",
                                    "King's Landing",
                                    tryList = listOf(
                                            Try(value = 859.0, date = "Avant-hier"),
                                            Try(value = 1258.0, date = "Le mois dernier")
                                    )
                            ),
                            AB(
                                    0,
                                    "Depuis moins le lointain",
                                    "Castelnau",
                                    "Montpellier",
                                    tryList = listOf(
                                            Try(value = 25.0, date = "Hier"),
                                            Try(value = 28.0, date = "La semaine dernière")
                                    )
                            )
                    )
            ))
        }

    }

    companion object : SingletonHolder<ABTestRepository, Context>(::ABTestRepository)

    fun getABTests(callbacks: ABTestsDataSource.LoadABTestsCallback) {
        callbacks.onABTestsLoaded(mAbTestBox.all)
    }

    fun getABTest(id: Long, callbacks: ABTestsDataSource.GetABTestCallback) {
        callbacks.onABTestsLoaded(mAbTestBox.get(id))
    }

    fun getAB(id: Long, callbacks: ABTestsDataSource.GetABCallback) {
        callbacks.onABLoaded(mAbBox.get(id))
    }

    fun deleteABTest(abTestId: Long) {
        mAbTestBox.remove(abTestId)
    }
}
