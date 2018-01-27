package org.wontology.gleneivey.starshipbot

import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

import org.wontology.gleneivey.starshipbot.app.*

fun main(args: Array<String>) {
    requireAll(require.context("src", true, js("/\\.css$/")))

    render(document.getElementById("root")) {
        app()
    }
}
