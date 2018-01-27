package org.wontology.gleneivey.starshipbot.app

import react.RClass
import react.RProps

import org.w3c.dom.Node

@JsModule("three")
external class Three {
    class Sides
    open class Material
    open class Geometry

    class Scene {
        fun add(ambientLight: AmbientLight)
        fun add(light: SpotLight)
        fun add(mesh: Mesh)
    }

    class AmbientLight(color: Int) {}

    class SpotLight(color: Int) {
        val position: LightPosition
    }

    open class Camera {
        fun updateProjectionMatrix()
        var zoom: Int
        var position: CameraCoordinates
        var rotation: CameraCoordinates
    }

    class PerspectiveCamera(
            fieldOfView: Double,
            aspectRatio: Double,
            frustumNearPlaneDistance: Double,
            frustumFarPlaneDistance: Double
    ) : Camera

    open class Renderer {
        fun render(scene: Scene, camera: Camera)
    }

    class WebGLRenderer(settings: RendererSettings) : Renderer {
        fun setSize(width: Int, height: Int)
        fun setClearColor(color: Int, alpha: Double)
        fun render(scene: Three.Scene, camera: PerspectiveCamera)
        var domElement: Node
    }

    class Color {
        fun setRGB(red: Double, green: Double, blue: Double)
    }

    class MeshPhongMaterial(settings: MaterialSettings) : Material

    class CylinderGeometry(radiusTop: Double, radiusBottom: Double,
                           height: Double, radialSegments: Int) : Geometry

    class Mesh(geometry: Geometry, material: Material)

    companion object {
        var DoubleSide: Sides
    }
}

external class LightPosition {
    fun set(x: Int, y: Int, z: Int)
}

external class CameraCoordinates {
    var x: Double
    var y: Double
    var z: Double
}

external interface RendererSettings {
    var alpha: Boolean
}
fun rendererSettings(): RendererSettings = js("{}")

external interface MaterialSettings {
    var color: Three.Color
    var specular: Int
    var flatShading: Boolean
    var side: Three.Sides
}
fun materialSettings(): MaterialSettings = js("{}")
