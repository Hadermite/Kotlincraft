package kotlincraft.world.entity

import kotlincraft.input.Input
import kotlincraft.math.Vector
import org.lwjgl.glfw.GLFW

class Player(position: Vector, size: Vector) : Entity("Player", "player", position, size) {

    override fun update(delta: Double) {
        var movementX = 0
        var movementY = 0

        if (Input.keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
            movementX--
        }
        if (Input.keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
            movementX++
        }
        if (Input.keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
            movementY++
        }
        if (Input.keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
            movementY--
        }

        position = position.add(Vector(movementX, movementY).multiply(delta * 3))
    }
}
