package fr.montpelliertechhub.abctestchronomtre


data class ABTestContainer(val title: String, val tries: List<ABTest>)

data class ABTest (
    val title : String,
    val from : String,
    val to: String ,
    val tries: List<Try>)

data class Try (
    val value : Double,
    val date  : String)

