package kotlincraft.manager

import kotlincraft.Scene

class SceneManager {

    var currentScene: Scene? = null

    fun update(delta: Double) {
        currentScene?.update(delta)
    }

    fun render() {
        currentScene?.camera?.setProjection()
        currentScene?.render()
    }
}
