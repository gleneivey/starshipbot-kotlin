package org.wontology.gleneivey.starshipbot.app

import react.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        val design = generateAShipDesign(100.0)
        starship(design)
    }
}

fun RBuilder.app() = child(App::class) {}
