package kotlincraft.game

import kotlincraft.Scene
import kotlincraft.Window
import kotlincraft.game.world.World

class Game(window: Window) : Scene(window) {

    private val world = World(camera)

    override fun update(delta: Double) {
        world.update(delta)
    }

    override fun render() {
        world.render()
    }
}
