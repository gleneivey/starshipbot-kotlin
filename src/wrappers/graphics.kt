package org.wontology.gleneivey.starshipbot.app

import react.setState
import kotlin.browser.document
import kotlin.math.cos
import kotlin.math.sin

fun initializeRendering(surfaceColor: SurfaceColor): Starship.StarshipState {
    val scene = Three.Scene()

    val ambientLight = Three.AmbientLight(0xaaaaaa)
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
    materialColor.setRGB(surfaceColor.r, surfaceColor.g, surfaceColor.b)
    val material = Three.MeshPhongMaterial(materialSettings().apply {
        color = materialColor
        specular = 0x0
        flatShading = false
        side = Three.DoubleSide
    })

    return Starship.StarshipState(
            renderer = renderer,
            scene = scene,
            material = material,
            camera = camera
    )
}

fun mountCanvas(renderer: Three.WebGLRenderer) {
    document.body!!.appendChild(renderer.domElement)
}

//fun initializeGraphicsAndState(surfaceColor: SurfaceColor): Starship.StarshipState {
//
//
//    val scale = 200.0
//    val rho = camera.zoom * scale
//    return Starship.StarshipState(
//            renderer = renderer,
//            scene = scene,
//            material = material,
//            camera = camera
//    )
//}

fun setDesign(design: DesignTree, scene: Three.Scene, material: Three.Material) {
    setDesignRecursively(design, scene, material)
}

fun setDesignRecursively(design: DesignTree,
                         scene: Three.Scene, material: Three.Material) {
    when (design) {
        is DesignLeaf -> {
            setAPrimitive(design, scene, material)
        }
        is DesignNode -> {
            design.children.forEach { child ->
                setDesignRecursively(child, scene, material)
            }
        }
    }
}

fun advanceState(state: Renderer.RendererState): Renderer.RendererState {
    return state.apply {
        rotationY = state.rotationY + 0.0034
        rotationZ = state.rotationZ + 0.0035
    }
}

fun renderDesign(props: Renderer.RendererProps, state: Renderer.RendererState) {
    // animation rotates camera; update rotation, then update position so
    //   it always points back to the origin
    val camera = props.camera
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
    props.renderer.render(props.scene, camera)
}
