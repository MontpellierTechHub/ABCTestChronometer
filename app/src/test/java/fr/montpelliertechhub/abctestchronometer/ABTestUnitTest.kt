package fr.montpelliertechhub.abctestchronometer

import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.models.Try
import org.junit.Assert
import org.junit.Test

/**
 * Test for {@link ABTestUnitTest}
 *
 * Created by Hugo Gresse on 21/09/2017.
 */

class ABTestUnitTest {
    @Test
    fun getBestTime_isCorrect() {
        val abTest: ABTest = ABTest(
                "Super titre",
                "Montpellier",
                "Paris",
                listOf(
                        Try(25.0, "today"),
                        Try(42.0, "now")
                ))

        Assert.assertEquals("getBestTime return 25", Try(25.0, "today"), abTest.getBestTime())
    }
}
