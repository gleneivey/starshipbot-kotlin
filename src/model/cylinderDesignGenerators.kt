package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun coaxialCylindersDesign(maxComponentExtent: Double) : DesignTree {
    val design = DesignNode()

    val numObjects = (random() * 5).toInt() + 4
    for (i in 0..numObjects) {
        val cylinder = aCylinder(maxComponentExtent)
        design.add(cylinder)
    }

    return design
}

fun offsetCoaxialCylindersDesign(maxComponentExtent: Double) : DesignTree {
    val design = DesignNode()

    val numObjects = (random() * 5).toInt() + 3
    for (i in 0..numObjects) {
        val cylinder = aCylinder(maxComponentExtent)
        design.add(cylinder)
    }

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
    val design = DesignNode()

    val numObjects = (random() * 5).toInt()
    for (i in 0..numObjects) {
        val cylinder = aCylinder(maxComponentExtent)
        design.add(cylinder)
    }

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
    val design = DesignNode()

    val numObjects = (random() * 5).toInt()
    for (i in 0..numObjects) {
        val cylinder = aCylinder(maxComponentExtent)
        design.add(cylinder)
    }

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
