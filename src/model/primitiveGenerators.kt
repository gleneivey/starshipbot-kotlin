package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun aCylinder(maxComponentExtent: Double) : DesignTree {
    val longerThanWide = random() > 0.25
    return when (longerThanWide) {
        false -> Cylinder(random() * maxComponentExtent, random() * maxComponentExtent)
        true -> {
            val dimensions = arrayOf(random() * maxComponentExtent, random() * maxComponentExtent)
            Cylinder(dimensions.min()!!, dimensions.max()!!)
        }
    }
}
