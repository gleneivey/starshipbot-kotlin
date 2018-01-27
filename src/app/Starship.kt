package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*



class StarshipProps : RProps {
    var count: Int? = null
}

class StarshipState(
        var scene: Three.Scene,
        var material: Three.MeshPhongMaterial,
        var camera: Three.PerspectiveCamera,

        var rho: Double,
        var rotationX: Double,
        var rotationY: Double,
        var rotationZ: Double,
        var positionZ: Double
) : RState



class Starship(props: StarshipProps) : RComponent<StarshipProps, StarshipState>(props) {
    override fun componentDidMount() {
        val populatedState = initializeRenderingIntoState()
        setState(populatedState, {})

        window.setInterval({
            setState(advanceState(state), {})
        }, 1000/60)
    }

    override fun RBuilder.render() {
        p("App-credits") {
            +"By "
            a("https://twitter.com/gleneivey") {
                +"@gleneivey"
            }
            +", Code at "
            a("https://github.com/gleneivey/starshipbot-kotlin") {
                +"github.com/gleneivey/starshipbot-kotlin"
            }
        }
    }
}

fun RBuilder.starship(count: Int = 0) = child(Starship::class) {
    attrs.count = count
}
