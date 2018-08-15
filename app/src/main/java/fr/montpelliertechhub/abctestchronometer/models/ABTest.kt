package fr.montpelliertechhub.abctestchronometer.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * A container holding a group of ABTests made.
 *
 * @param title title for the group of ABTests.
 * @property ab the group of ABTests.
 * @see AB
 */
@Entity
data class ABTest(
        @Id var id: Long = 0,
        val title: String = "") {

    @Backlink
    lateinit var ab: ToMany<AB>

    constructor(title: String, abList: List<AB>): this(0, title) {
        abList.forEach { ab.add(it)}
    }

    fun getBestWay(): AB? {
        return ab.filter {it.tries.isNotEmpty()}.sortedWith(compareBy{ it.getBestTime()?.value }).firstOrNull()
    }

}
