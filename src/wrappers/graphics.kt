package org.wontology.gleneivey.starshipbot.app

import react.setState
import kotlin.browser.document
import kotlin.math.cos
import kotlin.math.sin

fun initializeGraphicsAndState(): Starship.StarshipState {
    val scene = Three.Scene()

    val ambientLight = Three.AmbientLight(0x555555)
    scene.add(ambientLight)

    val light = Three.SpotLight(0xffffff)
    light.position.set(1000, 2000, 1000)
    scene.add(light)

    val camera = Three.PerspectiveCamera(
            75.0,
            window.innerWidth.toDouble() / window.innerHeight.toDouble(),
            0.1,
            10000.0
    )
    camera.zoom = 10

    val renderer = Three.WebGLRenderer(rendererSettings().apply {
        alpha = true
    })
    renderer.setSize(window.innerWidth, window.innerHeight)
    renderer.setClearColor(0xffffff, 0.0)

    val materialColor = Three.Color()
    materialColor.setRGB(0.57, 0.578, 0.492)
    val material = Three.MeshPhongMaterial(materialSettings().apply {
        color = materialColor
        specular = 0x0
        flatShading = false
        side = Three.DoubleSide
    })

    document.body!!.appendChild(renderer.domElement)

    val scale = 100.0
    val rho = camera.zoom * scale
    return Starship.StarshipState(
            renderer = renderer,
            scene = scene,
            material = material,
            camera = camera,
            rho = rho,
            rotationX = -0.3,
            rotationY = +0.3,
            rotationZ = +0.0,
            positionZ = rho
    )
}

fun setDesign(scene: Three.Scene, material: Three.Material) {
    val geometry = Three.CylinderGeometry(20.0, 20.0, 90.0, 100)
    val shape = Three.Mesh(geometry, material)
    scene.add(shape)
}

fun advanceState(state: Starship.StarshipState): Starship.StarshipState {
    return state.apply {
        rotationY = state.rotationY + 0.002
        rotationZ = state.rotationZ + 0.002
    }
}

fun renderDesign(state: Starship.StarshipState) {
    // animation rotates camera; update rotation, then update position so
    //   it always points back to the origin
    val camera = state.camera
    camera.rotation.x = state.rotationX
    camera.rotation.y = state.rotationY
    camera.rotation.z = state.rotationZ
    camera.position.x =
            state.rho * sin(state.rotationY)
    camera.position.y =
            state.rho * sin(-state.rotationX) * cos(state.rotationY)
    camera.position.z =
            state.rho * cos(-state.rotationX) * cos(state.rotationY)

    camera.updateProjectionMatrix()
    state.renderer.render(state.scene, camera)
}
