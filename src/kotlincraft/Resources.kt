package kotlincraft

import kotlincraft.graphics.Texture

object Resources {

    private val textureLoadConfigs = ArrayList<TextureLoadConfig>()

    private val textures = HashMap<String, Texture>()

    fun addTexture(key: String, textureName: String) {
        textureLoadConfigs.add(TextureLoadConfig(key, textureName))
    }

    fun loadTextures() {
        for (config in textureLoadConfigs) {
            textures[config.key] = Texture("res/textures/${config.textureName}", nearestFilter = config.nearestFilter)
        }
        textureLoadConfigs.clear()
    }

    fun getTexture(key: String): Texture? {
        return textures[key]
    }

    data class TextureLoadConfig(
        val key: String,
        val textureName: String,
        val nearestFilter: Boolean = false
    )
}