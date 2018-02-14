package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*

class Renderer(props: Renderer.RendererProps) : RComponent<Renderer.RendererProps, Renderer.RendererState>(props) {
    override fun RBuilder.render() {
println("Renderer.render")
//        renderDesign(props)
    }

    class RendererProps(
            var renderer: Three.Renderer,
            var scene: Three.Scene,
            var camera: Three.PerspectiveCamera
    ) : RProps

    class RendererState(
            var rho: Double,
            var rotationX: Double,
            var rotationY: Double,
            var rotationZ: Double,
            var positionZ: Double
    ) : RState
}

fun RBuilder.renderer(surfaceColor: SurfaceColor, design: DesignTree) =
        child(Renderer::class) {}
