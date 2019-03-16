package kotlincraft

fun main() {
    val window = Window()

    Resources.addTexture("tile_grass", "tiles/grass.jpg")

    Resources.addTexture("entity_player", "entities/player.png")

    Resources.loadTextures()

    window.start()
}
