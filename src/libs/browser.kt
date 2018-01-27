package org.wontology.gleneivey.starshipbot.app

external val window: Window
external class Window {
    fun setInterval(callback: () -> Unit, millis: Int)
    var innerWidth: Int
    var innerHeight: Int
}
