package kotlincraft.input

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWMouseButtonCallback

class Mouse : GLFWMouseButtonCallback() {

    private val buttonsPressed = ArrayList<Int>()
    private val listeners = ArrayList<Listener>()

    override fun invoke(window: Long, button: Int, action: Int, mods: Int) {
        when (action) {
            GLFW_PRESS -> {
                listeners.forEach { l -> l.onMouseButtonPress(button, mods) }
                buttonsPressed.add(button)
            }
            GLFW_RELEASE -> {
                listeners.forEach { l -> l.onMouseButtonRelease(button, mods) }
                buttonsPressed.remove(button)
            }
        }
    }

    fun update() {
        buttonsPressed.forEach { b ->
            listeners.forEach { l -> l.onMouseButtonDown(b) }
        }
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    interface Listener {
        fun onMouseButtonPress(button: Int, mods: Int)
        fun onMouseButtonDown(button: Int)
        fun onMouseButtonRelease(button: Int, mods: Int)
    }
}
