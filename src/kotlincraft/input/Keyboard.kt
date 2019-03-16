package kotlincraft.input

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWKeyCallback

class Keyboard : GLFWKeyCallback() {

    private val keys = Array(65536) { false }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key >= keys.size) return
        keys[key] = action != GLFW_RELEASE
    }

    fun isKeyDown(keyCode: Int): Boolean {
        if (keyCode >= keys.size) return false
        return keys[keyCode]
    }
}
