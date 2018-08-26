package fr.montpelliertechhub.abctestchronometer.utils

/**
 * Created by Hugo Gresse on 22/08/2018.
 */

object EspressoIdlingResource {

    private val RESOURCE = "GLOBAL"

    val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}