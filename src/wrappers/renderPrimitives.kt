package org.wontology.gleneivey.starshipbot.app

import kotlin.js.Math.PI

fun setAPrimitive(leaf: DesignLeaf, scene: Three.Scene, material: Three.Material) {
    val shape = when (leaf) {
        is Cylinder -> {
            val cylinder = Three.CylinderGeometry(leaf.radius, leaf.radius, leaf.length, 60)
            val mesh = Three.Mesh(cylinder, material)
            mesh.rotation.z = -PI/2
            mesh
        }
        is Disk -> {
            val cylinder = Three.CylinderGeometry(leaf.radius, leaf.radius, leaf.thick, 60)
            val mesh = Three.Mesh(cylinder, material)
            mesh
        }
        else ->
            throw IllegalArgumentException("Don't know what kind of primitive this is")
    }

    shape.position.x = leaf.offsetX
    shape.position.y = leaf.offsetY
    shape.position.z = leaf.offsetZ
    shape.rotation.x += leaf.rotationX
    shape.rotation.y += leaf.rotationY
    shape.rotation.z += leaf.rotationZ

    scene.add(shape)
}
