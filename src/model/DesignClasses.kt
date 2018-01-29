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

    open fun duplicate() : DesignTree {
        throw IllegalArgumentException("Child class mus re-implement")
        return DesignTree() // not a real implementation, children should reimplement
    }

    fun cloneOffsetAndAlignment(source: DesignTree) {
        offsetX = source.offsetX
        offsetY = source.offsetY
        offsetZ = source.offsetZ
        alignmentX = source.alignmentX
        alignmentY = source.alignmentY
        alignmentZ = source.alignmentZ
    }
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

    override fun duplicate(): DesignTree {
        val dupe = Cylinder(radius, length)
        dupe.cloneOffsetAndAlignment(this)
        return dupe
    }
}
