package org.wontology.gleneivey.starshipbot.app

import react.setState
import kotlin.browser.document

fun initializeThreeAndCanvasIntoState(): AppState {
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

    return AppState(
        scene = scene,
        material = material,
        camera = camera
    )
}