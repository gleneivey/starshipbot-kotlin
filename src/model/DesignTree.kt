package org.wontology.gleneivey.starshipbot.app

open class DesignTree {
    var offsetX = 0.0
    var offsetY = 0.0
    var offsetZ = 0.0
    var alignmentX = 0.0
    var alignmentY = 0.0
    var alignmentZ = 0.0
    var extentX = 0.0
    var extentY = 0.0
    var extentZ = 0.0
}

open class DesignNode : DesignTree() {
    var children = mutableListOf<DesignTree>()
    fun add(tree: DesignTree) {
        children.add(tree)
    }
}

open class DesignLeaf : DesignTree()

class Cylinder(val radius: Double, val length: Double) : DesignLeaf() {
    init {
        extentX = length
        extentY = radius
        extentZ = radius
    }
}