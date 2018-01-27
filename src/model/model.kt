package org.wontology.gleneivey.starshipbot.app

import react.RProps
import react.RState

class AppState(
        var scene: Three.Scene,
        var material: Three.MeshPhongMaterial,
        var camera: Three.PerspectiveCamera
) : RState

class AppProps : RProps
