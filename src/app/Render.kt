package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*

class Renderer(props: Renderer.RendererProps) : RComponent<Renderer.RendererProps, Renderer.RendererState>(props) {
    override fun componentDidMount() {
println("Renderer.componentDidMount")
    }


    override fun RBuilder.render() {
println("Renderer.render")
        renderDesign(props)
    }

    class RendererProps(
            var rho: Double,
            var rotationX: Double,
            var rotationY: Double,
            var rotationZ: Double,
            var positionZ: Double
    ) : RProps

    class RendererState(
            var renderer: Three.Renderer,
            var scene: Three.Scene,
            var material: Three.Material,
            var camera: Three.PerspectiveCamera
    ) : RState
}

fun RBuilder.renderer(surfaceColor: SurfaceColor, design: DesignTree) =
        child(Renderer::class) {}
