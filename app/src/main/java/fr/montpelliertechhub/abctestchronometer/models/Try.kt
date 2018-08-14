package fr.montpelliertechhub.abctestchronometer.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

/**
 * Represent an attempt for a specific AB.
 *
 * @param value the time elapsed during the attempt.
 * @property date the date of the attempt.
 */
@Entity
data class Try(
        @Id var id: Long = 0,
        val value: Double = 0.0,
        val date: String = ""
) {

    lateinit var ab: ToOne<AB>

    constructor(value: Double, date: String, abParam: AB): this(0, value, date) {
        ab.target = abParam
    }
}
