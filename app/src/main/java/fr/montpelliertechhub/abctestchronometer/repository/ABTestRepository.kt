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
                                    tries = listOf(
                                            Try(value = 859.0, date = "Avant-hier"),
                                            Try(value = 1258.0, date = "Le mois dernier")
                                    )
                            ),
                            ABTest(
                                    "Depuis moins le lointain",
                                    "Castelnau",
                                    "Montpellier",
                                    tries = listOf(
                                            Try(value = 25.0, date = "Hier"),
                                            Try(value = 28.0, date = "La semaine dernière")
                                    )
                            )
                    )
            ))

}
