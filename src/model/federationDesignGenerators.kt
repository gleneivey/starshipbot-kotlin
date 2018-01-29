package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random

fun federationShipDesign(maxComponentExtent: Double) : DesignTree {
    val design = DesignNode()

    val primaryHull = aBoundedDisk(maxComponentExtent, 0.7, 0.02, 0.35)
    offsetPrimaryHull(primaryHull)
    design.add(primaryHull)

    val secondaryHull = aBoundedCylinder(maxComponentExtent, 0.4, 0.20, 0.75)
    offsetSecondaryHull(secondaryHull)
    design.add(secondaryHull)

    val aNacelle = aBoundedCylinder(maxComponentExtent, 0.8, 0.04, 0.19)
    offsetNacelle(aNacelle, primaryHull.extentX)
    design.add(aNacelle)
    if (random() < 0.2) {
        addThreeMoreNacelles(design, aNacelle, secondaryHull)
    } else {
        addOneMoreNacelle(design, aNacelle)
    }

    val nacelles = design.children.slice(2..(design.children.size-1))
    val careAboutOverlap = random() > 0.333
    val (minXOffset, maxXOffset) = minAndMaxOverlappingX(secondaryHull, aNacelle)
//    if (careAboutOverlap && minXOffset < maxXOffset) {
        val perpendicularStruts = random() > 0.5
//        if (perpendicularStruts) {
            val x = minXOffset + ((maxXOffset - minXOffset) * random())
            nacelles.forEach { nacelle ->
                design.add(
                    narrowStrutBetween(
                            maxComponentExtent,
                            x, secondaryHull.offsetY, secondaryHull.offsetZ,
                            x, nacelle.offsetY, nacelle.offsetZ
                    )
                )
            }
//        } else {
//        }
//    } else {
//    }
    return design
}

fun narrowStrutBetween(
        maxComponentExtent: Double,
        fromX: Double, fromY: Double, fromZ: Double,
        toX: Double, toY: Double, toZ: Double
): DesignTree {
    val minRadius = 0.02 * maxComponentExtent
    val maxRadius = 0.1 * maxComponentExtent
    val radius = randomInRange(minRadius, maxRadius)
    val length = distanceBetween(fromX, fromY, fromZ, toX, toY, toZ)
    return Cylinder(radius, length)
}

fun offsetPrimaryHull(primary: DesignTree) {
    primary.offsetX += primary.extentX * randomInRange(0.1,1.2)
    primary.offsetY += primary.extentX * random() * 0.5
}

fun offsetSecondaryHull(secondary: DesignTree) {
    secondary.offsetX -= secondary.extentX * randomInRange(0.3, 1.2)
    secondary.offsetY -= secondary.extentY * random() * 1.2
}

fun offsetNacelle(nacelle: DesignTree, primaryLength: Double) {
    if (random() < 0.3) {
        nacelle.offsetX -= primaryLength * randomInRange(-0.6, 1.3)
    } else {
        nacelle.offsetX -= primaryLength * randomInRange(0.4, 1.5)
    }

    nacelle.offsetY += primaryLength * randomInRange(0.1, 1.2)
    nacelle.offsetZ += primaryLength * randomInRange(0.1, 1.2)
}

fun addOneMoreNacelle(design: DesignNode, nacelle: DesignTree) {
    val second = nacelle.duplicate()
    second.offsetZ = -nacelle.offsetZ
    design.add(second)
}

fun addThreeMoreNacelles(
        design: DesignNode,
        nacelle: DesignTree,
        secondaryHull: DesignTree
) {
    addOneMoreNacelle(design, nacelle)

    val belowSecondaryHull = -nacelle.offsetY + secondaryHull.offsetY
    val third = nacelle.duplicate()
    third.offsetY = belowSecondaryHull
    third.offsetZ = nacelle.offsetZ
    design.add(third)

    val fourth = nacelle.duplicate()
    fourth.offsetY = belowSecondaryHull
    fourth.offsetZ = -nacelle.offsetZ
    design.add(fourth)
}

