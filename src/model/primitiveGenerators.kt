package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun aCylinder(maxComponentExtent: Double): DesignTree {
    val longerThanWide = random() > 0.25
    return when (longerThanWide) {
        false -> Cylinder(random() * maxComponentExtent, random() * maxComponentExtent)
        true -> {
            val dimensions = arrayOf(random() * maxComponentExtent, random() * maxComponentExtent)
            Cylinder(dimensions.min()!!, dimensions.max()!!)
        }
    }
}

fun aBoundedDisk(
        maxComponentExtent: Double,
        minExtentFraction: Double,
        minThickRadiusRatio: Double?,
        maxThickRadiusRatio: Double?
): DesignTree {
    val randomExtent = 1.0 - minExtentFraction
    val radius = maxComponentExtent * (minExtentFraction +
            (random() * randomExtent))

    var thick = 0.0
    if (minThickRadiusRatio != null && maxThickRadiusRatio != null) {
        val ratio = randomInRange(minThickRadiusRatio, maxThickRadiusRatio)
        thick = ratio * radius
    } else if (minThickRadiusRatio != null) {
        do {
            thick = maxComponentExtent * random()
        } while (thick/radius < minThickRadiusRatio)
    } else if (maxThickRadiusRatio != null) {
        do {
            thick = maxComponentExtent * random()
        } while (thick/radius > maxThickRadiusRatio)
    }

    return Disk(radius, thick)
}

fun aBoundedCylinder(
        maxComponentExtent: Double,
        maxRadius: Double?,
        minLengthFraction: Double,
        minRadiusLengthRatio: Double?,
        maxRadiusLengthRatio: Double?
): DesignTree {
    val randomExtent = 1.0 - minLengthFraction
    val length = maxComponentExtent * (minLengthFraction +
            (random() * randomExtent))

    var radius = 0.0
    if (minRadiusLengthRatio != null && maxRadiusLengthRatio != null) {
        do {
            val ratio = randomInRange(minRadiusLengthRatio, maxRadiusLengthRatio)
            radius = ratio * length
        } while (maxRadius != null && radius > maxRadius)
    } else if (minRadiusLengthRatio != null) {
        do {
            radius = maxComponentExtent * random()
        } while (radius/length < minRadiusLengthRatio ||
                (maxRadius != null && radius > maxRadius))
    } else if (maxRadiusLengthRatio != null) {
        do {
            radius = maxComponentExtent * random()
        } while (radius/length > maxRadiusLengthRatio ||
                (maxRadius != null && radius > maxRadius))
    }

    return Cylinder(radius, length)
}
