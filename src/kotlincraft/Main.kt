package kotlincraft

import kotlincraft.game.Game

fun main() {
    val window = Window()

    Resources.addTexture("tile_grass", "tiles/grass.jpg")
    Resources.addTexture("entity_player", "entities/player.png")

    Resources.loadTextures()

    window.sceneManager.currentScene = Game(window)

    window.start()
}
