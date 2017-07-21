package fr.montpelliertechhub.abctestchronometer.models

/**
 * Represent an attempt for a specific ABTest.
 *
 * @param value the time elapsed during the attempt.
 * @property date the date of the attempt.
 */
data class Try (
    val value : Double,
    val date  : String
)
