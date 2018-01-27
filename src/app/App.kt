package org.wontology.gleneivey.starshipbot.app

import react.*
import react.dom.*

class App : RComponent<AppProps, AppState>() {
    override fun componentDidMount() {
        setState(initializeThreeAndCanvasIntoState(), {})
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

fun RBuilder.app() = child(App::class) {}
