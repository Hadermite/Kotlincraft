package kotlincraft

import kotlincraft.math.Vector

abstract class Scene(
    val window: Window,
    val camera: Camera = Camera(window, 10.0, Vector.zero)
) {

    abstract fun update(delta: Double)
    abstract fun render()
}
