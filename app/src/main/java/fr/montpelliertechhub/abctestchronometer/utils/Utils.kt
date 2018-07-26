package fr.montpelliertechhub.abctestchronometer.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * General utility for the app
 *
 * Created by Hugo Gresse on 21/07/2017.
 */

infix fun ViewGroup.inflate(@LayoutRes res: Int): View =
        LayoutInflater.from(this.context).inflate(res, this, false)
