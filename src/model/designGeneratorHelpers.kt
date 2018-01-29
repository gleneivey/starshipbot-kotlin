package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.PI
import kotlin.js.Math.random
import kotlin.js.Math.sin
import kotlin.js.Math.cos


data class ValueAndIndex(val value: Double, val index: Int)
fun maxWidthOf(designs: MutableList<DesignTree>) : ValueAndIndex {
    var widestDesignIndex = 0
    var maxWidth = 0.0;
    for ((i, design) in designs.withIndex()) {
        if (design.extentY > maxWidth) {
            widestDesignIndex = i
            maxWidth = design.extentY
        }
        if (design.extentZ > maxWidth) {
            widestDesignIndex = i
            maxWidth = design.extentZ
        }
    }

    return ValueAndIndex(maxWidth, widestDesignIndex)
}

fun randomlyPositionObjectForeAndAft(design: DesignTree) {
    val algorithmSelect = (random() * 4).toInt()
    when (algorithmSelect) {
        0 -> design.offsetX = design.extentX / 2
        1 -> design.offsetX = -design.extentX / 2
        2 -> design.offsetX = (random() * design.extentX) - (design.extentX / 2)
    }
}

fun randomlyPositionObjectInAthwartships(maxRadius: Double, design: DesignTree) {
    val rho = random() * maxRadius * 1.1
    val theta = random() * 2 * PI
    val y = rho * cos(theta)
    val z = rho * sin(theta)

    val algorithmSelect = (random() * 4).toInt()
    when (algorithmSelect) {
        0 -> design.offsetY = y
        1 -> design.offsetZ = z
        2 -> {
            design.offsetY = y
            design.offsetZ = z
        }
    }
}

fun addSymmetricRandomlyAthwartshipsPositionedObjects(
        count: Int,
        maxRadius: Double,
        design: DesignTree,
        node: DesignNode) {
    val rho = random() * maxRadius * 1.1
    var theta = random() * 2 * PI

    var y = rho * cos(theta)
    var z = rho * sin(theta)
    design.offsetY = y
    design.offsetZ = z

    val rotation = (2*PI) / count
    for (i in 1..(count-1)) {
        var newObject = design.duplicate()
        theta += rotation
        y = rho * cos(theta)
        z = rho * sin(theta)
        newObject.offsetY = y
        newObject.offsetZ = z
        node.add(newObject)
    }
}
