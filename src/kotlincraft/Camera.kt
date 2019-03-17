package kotlincraft

import org.lwjgl.opengl.GL11.*
import kotlincraft.math.Vector

class Camera(var window: Window, val viewportHeight: Double, var position: Vector) : Window.ResizeListener {

    var viewportWidth: Double = 0.0; private set

    init {
        window.addResizeListener(this)
        calculateViewportWidth()
    }

    fun setProjection() {
        glLoadIdentity()
        glOrtho(-viewportWidth / 2, viewportWidth / 2, -viewportHeight / 2, viewportHeight / 2, 1.0, 0.0)
        glTranslated(-position.x, -position.y, 0.0)
        glViewport(0, 0, window.size.x.toInt(), window.size.y.toInt())
    }

    fun translate(translation: Vector) {
        position = position.add(translation)
    }

    fun dispose() {
        window.removeResizeListener(this)
    }

    private fun calculateViewportWidth() {
        viewportWidth = viewportHeight * window.aspectRatio
    }

    override fun onWindowResize(size: Vector) {
        calculateViewportWidth()
    }
}
