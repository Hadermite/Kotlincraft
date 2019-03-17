package kotlincraft.game.world

import kotlincraft.math.Vector

class Chunk(private val position: Vector) {

    companion object {
        const val size = 10
    }

    private val tileRows: Array<Array<Tile>>

    init {
        tileRows = Array(size) { y ->
            Array(size) { x ->
                Tile(Tile.Type.GRASS, Vector(position.x + x, position.y + y))
            }
        }
    }

    fun render() {
        for (row in tileRows) {
            for (tile in row) {
                tile.render()
            }
        }
    }
}
