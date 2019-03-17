package kotlincraft.game.world

import kotlincraft.Resources
import kotlincraft.graphics.Texture
import kotlincraft.math.Vector

class Tile(val type: Type, val position: Vector) {

    fun render() {
        type.texture?.render(position, Vector.one)
    }

    enum class Type(val displayName: String, textureKey: String?) {

        AIR("Air", null),
        GRASS("Grass", "grass");

        val texture: Texture? = Resources.getTexture("tile_$textureKey")
    }
}
