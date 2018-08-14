package fr.montpelliertechhub.abctestchronometer

import fr.montpelliertechhub.abctestchronometer.models.AB
import fr.montpelliertechhub.abctestchronometer.models.Try
import org.junit.Assert
import org.junit.Test

/**
 * Test for {@link ABTestUnit}
 *
 * Created by Hugo Gresse on 21/09/2017.
 */

class ABTestUnit {
    @Test
    fun getBestTime_isCorrect() {
        val ab: AB = AB(
                "Super titre",
                "Montpellier",
                "Paris",
                listOf(
                        Try(25.0, "today"),
                        Try(42.0, "now")
                ))

        Assert.assertEquals("getBestTime return 25", Try(25.0, "today"), ab.getBestTime())
    }
}
