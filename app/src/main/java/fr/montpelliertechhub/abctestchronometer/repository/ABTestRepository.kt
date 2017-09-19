package fr.montpelliertechhub.abctestchronometer.repository

import fr.montpelliertechhub.abctestchronometer.models.ABTest
import fr.montpelliertechhub.abctestchronometer.models.ABTestContainer
import fr.montpelliertechhub.abctestchronometer.models.Try

object ABTestRepository {

    val abTestContainer: MutableList<ABTestContainer> = mutableListOf(ABTestContainer(
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
    ), ABTestContainer(
                    title = "GOT",
                    abtests = listOf(
                            ABTest(
                                    "De l'autre côté",
                                    "Bravoos",
                                    "King's Landing",
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
            ))

}
