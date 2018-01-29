package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun coaxialCylindersDesign(maxComponentExtent: Double) : DesignTree {
    return aDesignWithSeveralSubdesigns(4, 5) { aCylinder(maxComponentExtent) }
}

fun offsetCoaxialCylindersDesign(maxComponentExtent: Double) : DesignTree {
    val design = aDesignWithSeveralSubdesigns(3, 5) { aCylinder(maxComponentExtent) }
    for (cylinder in design.children) {
        val entirelyContainingCylinders = design.children.filter {
            it.extentX > cylinder.extentX &&
                    it.extentY > cylinder.extentY &&
                    it.extentZ > cylinder.extentZ
        }

        if (entirelyContainingCylinders.isNotEmpty()) {
            val containing = entirelyContainingCylinders.first()
            if (random() > 0.5) {
                cylinder.offsetX += containing.extentX/2
            } else {
                cylinder.offsetX -= containing.extentX/2
            }
        }
    }

    return design
}

fun cylinderClusterDesign(maxComponentExtent: Double) : DesignTree {
    val design = aDesignWithSeveralSubdesigns(1, 4) { aCylinder(maxComponentExtent) }
    val (widestRadius, widestCylinderIndex) = maxWidthOf(design.children)
    for ((i, cylinder) in design.children.withIndex()) {
        if (i != widestCylinderIndex) {
            randomlyPositionObjectForeAndAft(cylinder)
            randomlyPositionObjectInAthwartships(widestRadius, cylinder)
        }
    }

    return design
}

fun symmetricCylinderClusterDesign(maxComponentExtent: Double) : DesignTree {
    val design = aDesignWithSeveralSubdesigns(1, 4) { aCylinder(maxComponentExtent) }
    val (widestRadius, widestCylinderIndex) = maxWidthOf(design.children)
    val originalNumberOfChildren = design.children.size
    for (i in 0..(originalNumberOfChildren-1)) {
        var cylinder = design.children[i]
        if (i != widestCylinderIndex) {
            randomlyPositionObjectForeAndAft(cylinder)
            val numSymmeticCylinders = (random() * 4).toInt() + 1 // 1..4
            addSymmetricRandomlyAthwartshipsPositionedObjects(
                    numSymmeticCylinders,
                    widestRadius,
                    cylinder,
                    design
            )
        }
    }

    return design
}
