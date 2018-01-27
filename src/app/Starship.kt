package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*

class Starship(props: Starship.StarshipProps) : RComponent<Starship.StarshipProps, Starship.StarshipState>(props) {
    override fun componentDidMount() {
        val populatedState = initializeGraphicsAndState()
        setState(populatedState, {})

        setDesign(populatedState.scene, populatedState.material)

        window.setInterval({
            setState(advanceState(state), {})
            renderDesign(state)
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


    class StarshipProps : RProps {
        var count: Int? = null
    }

    class StarshipState(
            var renderer: Three.Renderer,
            var scene: Three.Scene,
            var material: Three.Material,
            var camera: Three.PerspectiveCamera,

            var rho: Double,
            var rotationX: Double,
            var rotationY: Double,
            var rotationZ: Double,
            var positionZ: Double
    ) : RState
}

fun RBuilder.starship(count: Int = 0) = child(Starship::class) {
    attrs.count = count
}
