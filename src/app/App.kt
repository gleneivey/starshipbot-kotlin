package org.wontology.gleneivey.starshipbot.app

import react.*

class App : RComponent<RProps, App.AppState>() {
    override fun componentDidMount() {
println("App.componentDidMount")
        setState(App.AppState(designCount = 1))

        window.setInterval({
            setState(App.AppState(designCount = state.designCount+1))
        }, 45 * 1000)
    }

    override fun RBuilder.render() {
println("App.render, designCount = ${state.designCount}")
        val design = generateAShipDesign(100.0)
        val surfaceColor = aRandomSurfaceColor()
        starship(surfaceColor, design)
    }

    class AppState(var designCount: Int) : RState
}

fun RBuilder.app() = child(App::class) {}
