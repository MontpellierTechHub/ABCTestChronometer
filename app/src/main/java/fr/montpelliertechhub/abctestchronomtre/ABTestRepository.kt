package fr.montpelliertechhub.abctestchronomtre

object ABTestRepository {

    val abTestList : MutableList<ABTestContainer> = mutableListOf(ABTestContainer(
            title= "MAUG",
            tries = mutableListOf(
                    ABTest("Depuis le lointain", "Baillargues", "Montpellier", tries = mutableListOf()),
                    ABTest("Depuis moins le lointain", "Castelnau", "Montpellier", tries = mutableListOf(Try(value=25.0, date="Hier")))
            )))

}