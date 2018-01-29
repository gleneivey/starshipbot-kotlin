package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*

class Starship(props: Starship.StarshipProps) : RComponent<Starship.StarshipProps, Starship.StarshipState>(props) {
    override fun componentDidMount() {
        val populatedState = initializeGraphicsAndState(props.surfaceColor!!)
        setState(populatedState, {})

        setDesign(props.design!!, populatedState.scene, populatedState.material)

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


    class StarshipProps(
            var surfaceColor: SurfaceColor?,
            var design: DesignTree?
    ) : RProps

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

fun RBuilder.starship(surfaceColor: SurfaceColor, design: DesignTree) =
        child(Starship::class) {
            attrs.surfaceColor = surfaceColor
            attrs.design = design
        }
