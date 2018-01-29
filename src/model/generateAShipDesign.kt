package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun generateAShipDesign(maxComponentExtent: Double) : DesignTree {
    val algorithmSelect = random()
    val topNode = when {
        (algorithmSelect < 0.04) -> coaxialCylindersDesign(maxComponentExtent)
        (algorithmSelect < 0.08) -> offsetCoaxialCylindersDesign(maxComponentExtent)
        (algorithmSelect < 0.16) -> cylinderClusterDesign(maxComponentExtent)
        (algorithmSelect < 0.333) -> symmetricCylinderClusterDesign(maxComponentExtent)
        else -> federationShipDesign(maxComponentExtent)
    }

    return topNode
}
