package kotlincraft.input

import kotlincraft.math.Vector
import org.lwjgl.glfw.GLFWCursorPosCallback

class Cursor : GLFWCursorPosCallback() {

    var position: Vector = Vector.zero; private set

    override fun invoke(window: Long, xpos: Double, ypos: Double) {
        position = Vector(xpos, ypos)
    }
}
