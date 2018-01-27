package org.wontology.gleneivey.starshipbot.app

import react.RClass
import react.RProps

import org.w3c.dom.Node

@JsModule("three")
external class Three {
    class Scene {
        fun add(ambientLight: AmbientLight)
        fun add(light: SpotLight)
    }

    class AmbientLight(color: Int) {}

    class SpotLight(color: Int) {
        val position: Position
    }

    class PerspectiveCamera(
            fieldOfView: Double,
            aspectRatio: Double,
            frustumNearPlaneDistance: Double,
            frustumFarPlaneDistance: Double
    ) {}

    class WebGLRenderer(settings: RendererSettings) {
        fun setSize(width: Int, height: Int)
        fun setClearColor(color: Int, alpha: Double)
        var domElement: Node
    }

    class Color {
        fun setRGB(red: Double, green: Double, blue: Double)
    }

    class MeshPhongMaterial(settings: MaterialSettings) {}

    companion object {
        var DoubleSide: Sides
    }
}

external class Position {
    fun set(x: Int, y: Int, z: Int)
}

external interface RendererSettings {
    var alpha: Boolean
}
fun RendererSettings(): RendererSettings = js("{}")

external interface MaterialSettings {
    var color: Three.Color
    var specular: Int
    var flatShading: Boolean
    var side: Sides
}
fun MaterialSettings(): MaterialSettings = js("{}")

external class Sides {}
