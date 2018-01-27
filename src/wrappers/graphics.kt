package org.wontology.gleneivey.starshipbot.app

import react.setState
import kotlin.browser.document

fun initializeRenderingIntoState(): StarshipState {
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

    val renderer = Three.WebGLRenderer(RendererSettings().apply {
        alpha = true
    })
    renderer.setSize(window.innerWidth, window.innerHeight)
    renderer.setClearColor(0xffffff, 0.0)

    val materialColor = Three.Color()
    materialColor.setRGB(0.57, 0.578, 0.492)
    val material = Three.MeshPhongMaterial(MaterialSettings().apply {
        color = materialColor
        specular = 0x0
        flatShading = false
        side = Three.DoubleSide
    })

    document.body!!.appendChild(renderer.domElement)

    return StarshipState(
        scene = scene,
        material = material,
        camera = camera,
        rho = 0.0,
        rotationX = -0.3,
        rotationY = +0.3,
        rotationZ = +0.0,
        positionZ = 0.0
    )
}

fun advanceState(state: StarshipState): StarshipState {
    return state.apply {
        rotationY = state.rotationY + 0.002
        rotationZ = state.rotationZ + 0.002
    }
}
