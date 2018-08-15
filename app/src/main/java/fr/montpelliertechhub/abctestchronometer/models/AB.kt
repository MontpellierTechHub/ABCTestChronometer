package fr.montpelliertechhub.abctestchronometer.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

/**
 * Represent a test to determine the time it takes to travel from point A to point B.
 *
 * @param title A title for the AB.
 * @property from the starting point of the travel (point A).
 * @property to the point of arrival of the travel (point B).
 * @property tries all the tries made for this AB.
 * @see Try
 */
@Entity
data class AB(
        @Id var id: Long = 0,
        val title: String = "",
        val from: String = "",
        val to: String = "") {

    lateinit var abTest: ToOne<ABTest>

    @Backlink
    lateinit var tries: ToMany<Try>

    constructor(id: Long, title: String, from: String, to: String, test: ABTest) : this(id, title, from, to) {
        abTest.target = test
    }

    constructor(id: Long, title: String, from: String, to: String, tryList: List<Try>) : this(id, title, from, to) {
        tryList.forEach { tries.add(it) }
    }


    fun getBestTime(): Try? {
        return tries.sortedWith(compareBy { it.value }).firstOrNull()
    }

}
