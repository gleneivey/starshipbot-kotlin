package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.PI
import kotlin.js.Math.acos
import kotlin.js.Math.atan
import kotlin.js.Math.cos
import kotlin.js.Math.random

fun federationShipDesign(maxComponentExtent: Double) : DesignTree {
    val design = DesignNode()

    val primaryHull = aBoundedDisk(maxComponentExtent*0.8, 0.7, 0.02, 0.35)
    offsetPrimaryHull(primaryHull)
    design.add(primaryHull)

    val secondaryHull = aBoundedCylinder(maxComponentExtent*1.8, null, 0.4, 0.03, 0.35)
    offsetSecondaryHull(secondaryHull)
    design.add(secondaryHull)

    val aNacelle = aBoundedCylinder(
            maxComponentExtent*1.4, secondaryHull.extentY,
            0.8, 0.04, 0.19
    )
    offsetNacelle(aNacelle, primaryHull.extentX)
    design.add(aNacelle)
    if (random() < 0.2) {
        addThreeMoreNacelles(design, aNacelle, secondaryHull)
    } else {
        addOneMoreNacelle(design, aNacelle)
    }
    val nacelles = design.children.slice(2..(design.children.size-1))

    addNacelleStruts(design, nacelles, secondaryHull, aNacelle)
//    addStrutBetweenHulls(design, primaryHull, secondaryHull)
    return design
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

fun addNacelleStruts(
        design: DesignNode,
        nacelles: List<DesignTree>,
        secondaryHull: DesignTree,
        aNacelle: DesignTree
) {
    val nacelleRadius = if (aNacelle is Cylinder) {
        aNacelle.radius
    } else {
        0.0
    }
    val maxStrutRadius = nacelleRadius * 0.45
    val minStrutRadius = nacelleRadius * 0.15
    val strutRadius = randomInRange(minStrutRadius, maxStrutRadius)

    val careAboutOverlap = random() > 0.333
    var (minXOffset, maxXOffset) = minAndMaxOverlappingX(secondaryHull, aNacelle)
//println("overlapping $minXOffset--$maxXOffset")
    minXOffset += aNacelle.extentY
    maxXOffset -= aNacelle.extentY
    if (careAboutOverlap && minXOffset < maxXOffset) {
        val perpendicularStruts = random() > 0.5
        if (perpendicularStruts) {
            val x = randomInRange(minXOffset, maxXOffset)
            nacelles.forEach { nacelle -> design.add(
                    aNarrowStrutBetween(
                            strutRadius,
                            x, secondaryHull.offsetY, secondaryHull.offsetZ,
                            x, nacelle.offsetY, nacelle.offsetZ
                    )
            ) }
        } else {
            val x1 = randomInRange(minXOffset, maxXOffset)
            val x2 = randomInRange(minXOffset, maxXOffset)
            nacelles.forEach { nacelle -> design.add(
                    aNarrowStrutBetween(
                            strutRadius,
                            x1, secondaryHull.offsetY, secondaryHull.offsetZ,
                            x2, nacelle.offsetY, nacelle.offsetZ
                    )
            ) }
        }
    } else {
        val x1 = randomInRange(
                secondaryHull.offsetX - secondaryHull.extentX/2,
                secondaryHull.offsetX + secondaryHull.extentX/2
        )
        val x2 = randomInRange(
                aNacelle.offsetX - aNacelle.extentX/2,
                aNacelle.offsetX + aNacelle.extentX/2
        )
        nacelles.forEach { nacelle -> design.add(
                aNarrowStrutBetween(
                        strutRadius,
                        x1, secondaryHull.offsetY, secondaryHull.offsetZ,
                        x2, nacelle.offsetY, nacelle.offsetZ
                )
        ) }
    }
}

fun aNarrowStrutBetween(
        radius: Double,
        fromX: Double, fromY: Double, fromZ: Double,
        toX: Double, toY: Double, toZ: Double
): DesignTree {
    val length = distanceBetween(fromX, fromY, fromZ, toX, toY, toZ)
    val strut = Cylinder(radius, length)

    // rotate strut to match angle between 'from' and 'to'
    strut.rotationZ = PI /2    // start from vertical
    val xDiff = toX - fromX
    val yDiff = toY - fromY
    val zDiff = toZ - fromZ
    strut.rotationZ -= atan(xDiff / yDiff)
    strut.rotationX += atan(zDiff / yDiff)

    // translate strut's center to midpoint of line between 'from' and 'to'
    strut.offsetX = (fromX+toX)/2
    strut.offsetY = (fromY+toY)/2
    strut.offsetZ = (fromZ+toZ)/2

    return strut
}

fun addStrutBetweenHulls(
        design: DesignNode,
        primaryHull: DesignTree,
        secondaryHull: DesignTree
) {
    val maxStrutRadius = secondaryHull.extentY * 0.8
    val minStrutRadius = secondaryHull.extentY * 0.2
    val strutRadius = randomInRange(minStrutRadius, maxStrutRadius)
    val maxPrimary = primaryHull.offsetX - strutRadius
    val minPrimary = (primaryHull.offsetX - primaryHull.extentX/2) + strutRadius
    val maxSecondary = (secondaryHull.offsetX + secondaryHull.extentX/2) - strutRadius
    val minSecondary = secondaryHull.offsetX + strutRadius

    design.add(
            aThickStrutBetween(
                    strutRadius,
                    randomInRange(maxPrimary, minPrimary), primaryHull.offsetY, 0.0,
                    randomInRange(maxSecondary, minSecondary), secondaryHull.offsetY, 0.0
            )
    )
}

fun aThickStrutBetween(
        radius: Double,
        fromX: Double, fromY: Double, fromZ: Double,
        toX: Double, toY: Double, toZ: Double
): DesignTree {
    val length = distanceBetween(fromX, fromY, fromZ, toX, toY, toZ)
    val strut = Cylinder(radius, length)

    // rotate strut to match angle between 'from' and 'to'
    strut.rotationZ = PI /2    // start from vertical
    val xDiff = toX - fromX
    val yDiff = toY - fromY
    strut.rotationZ -= atan(xDiff / yDiff)

    // translate strut's center to midpoint of line between 'from' and 'to'
    strut.offsetX = (fromX+toX)/2
    strut.offsetY = (fromY+toY)/2
    strut.offsetZ = (fromZ+toZ)/2

    return strut
}
