package fr.montpelliertechhub.abctestchronometer.models

/**
 * Represent a test to determine the time it takes to travel from point A to point B.
 *
 * @param title A title for the ABTest.
 * @property from the starting point of the travel (point A).
 * @property to the point of arrival of the travel (point B).
 * @property tries all the tries made for this ABTest.
 * @see Try
 */
data class ABTest (
    val title : String,
    val from : String,
    val to: String ,
    val tries: List<Try>
)
