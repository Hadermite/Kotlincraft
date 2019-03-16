package kotlincraft.world.entity

import kotlincraft.GameObject
import kotlincraft.Resources
import kotlincraft.graphics.Texture
import kotlincraft.input.Input
import kotlincraft.math.Vector
import org.lwjgl.glfw.GLFW

class Entity(val name: String, textureName: String, position: Vector, size: Vector) : GameObject(position, size) {

    private val texture: Texture? = Resources.getTexture("entity_$textureName")

    fun update(delta: Double) {
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

    fun render() {
        if (texture == null) return
        texture.render(position, size)
    }
}
