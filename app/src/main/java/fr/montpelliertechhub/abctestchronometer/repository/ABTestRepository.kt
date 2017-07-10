package fr.montpelliertechhub.abctestchronometer.repository

import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.models.ABTestContainer
import fr.montpelliertechhub.abctestchronometer.models.Try

object ABTestRepository {

    val abTestContainer: ABTestContainer = ABTestContainer(
        title = "MAUG",
        abtests = listOf(
            ABTest(
                "Depuis le lointain",
                "Baillargues",
                "Montpellier",
                tries = listOf()
            ),
            ABTest(
                "Depuis moins le lointain",
                "Castelnau",
                "Montpellier",
                tries = listOf(
                    Try(value = 25.0, date = "Hier")
                )
            )
        )
    )

}
