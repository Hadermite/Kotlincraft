package kotlincraft.world.entity

import kotlincraft.GameObject
import kotlincraft.Resources
import kotlincraft.graphics.Texture
import kotlincraft.math.Vector

abstract class Entity(val name: String, textureName: String, position: Vector, size: Vector) : GameObject(position, size) {

    private val texture: Texture? = Resources.getTexture("entity_$textureName")

    abstract fun update(delta: Double)

    override fun render() {
        if (texture == null) return
        texture.render(position, size)
    }
}
