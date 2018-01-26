package app

import react.*
import react.dom.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        p("App-intro") {
            +"Mmmmm...... a bot.... for starships...."
        }
    }
}

fun RBuilder.app() = child(App::class) {}
