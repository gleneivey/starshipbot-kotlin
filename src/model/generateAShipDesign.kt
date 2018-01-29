package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun generateAShipDesign(maxComponentExtent: Double) : DesignTree {
    val algorithmSelect = random()
    val topNode = when {
        (algorithmSelect < 0.08) -> coaxialCylindersDesign(maxComponentExtent)
        (algorithmSelect < 0.16) -> offsetCoaxialCylindersDesign(maxComponentExtent)
        (algorithmSelect < 0.32) -> cylinderClusterDesign(maxComponentExtent)
        else -> symmetricCylinderClusterDesign(maxComponentExtent)
    }

    return topNode
}
