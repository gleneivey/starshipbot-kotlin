package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.random


class SurfaceColor(val r: Double, val g: Double, val b: Double)

fun aRandomSurfaceColor() : SurfaceColor {
    val anchorColors = arrayOf(
            SurfaceColor(0x52/255.0, 0x52/255.0, 0x50/255.0),
            SurfaceColor(0x92/255.0, 0x9a/255.0, 0x7c/255.0),
            SurfaceColor(0x21/255.0, 0x26/255.0, 0x27/255.0),
            SurfaceColor(0x92/255.0, 0xa2/255.0, 0xac/255.0),
            SurfaceColor(0x29/255.0, 0x29/255.0, 0x35/255.0),
            SurfaceColor(0x57/255.0, 0x50/255.0, 0x7c/255.0),
            SurfaceColor(0x76/255.0, 0x79/255.0, 0x80/255.0),
            SurfaceColor(0x54/255.0, 0x5c/255.0, 0x74/255.0)
    )
    val numAnchors = anchorColors.size

    val oneColor = anchorColors[(random() * numAnchors).toInt()]
    val twoColor = anchorColors[(random() * numAnchors).toInt()]

    return SurfaceColor(
            (oneColor.r + twoColor.r) / 2,
            (oneColor.g + twoColor.g) / 2,
            (oneColor.b + twoColor.b) / 2
    )
}
