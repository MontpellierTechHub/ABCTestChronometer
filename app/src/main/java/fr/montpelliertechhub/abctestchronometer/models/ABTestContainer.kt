package fr.montpelliertechhub.abctestchronometer.models

/**
 * A container holding a group of ABTests made.
 *
 * @param title title for the group of ABTests.
 * @property abtests the group of ABTests.
 * @see ABTest
 */
data class ABTestContainer(
        val title: String,
        val abtests: List<ABTest> ) {


    fun getBestWay(): ABTest? {
        return abtests.sortedWith(compareBy{ it.getBestTime()?.value }).firstOrNull()
    }

}
