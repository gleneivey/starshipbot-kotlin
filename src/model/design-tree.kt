package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun generateAShipDesign(maxComponentExtent: Double) : DesignTree {
    val topNode = DesignNode()
    val cylinder = Cylinder(random() * maxComponentExtent, random() * maxComponentExtent)
    topNode.add(cylinder)

    return topNode
}
