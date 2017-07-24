package fr.montpelliertechhub.abctestchronometer.utils

import android.app.Activity
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * General utility for the app
 *
 * Created by Hugo Gresse on 21/07/2017.
 */

inline fun <reified V : View> View.bindView(resId: Int): ReadOnlyProperty<View, V> = Delegate {
    this.findViewById<V>(resId)
}

inline fun <reified V : View> Activity.bindView(resId: Int): ReadOnlyProperty<Activity, V> = Delegate {
    this.findViewById<V>(resId)
}

infix fun ViewGroup.inflate(@LayoutRes res: Int): View =
        LayoutInflater.from(this.context).inflate(res, this, false)

class Delegate<in A, out V>(val funFindView: (A) -> V) : ReadOnlyProperty<A, V> {
    override fun getValue(thisRef: A, property: KProperty<*>): V {
        return funFindView(thisRef)
    }
}