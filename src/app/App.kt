package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        starship(4)
    }
}

fun RBuilder.app() = child(App::class) {}
