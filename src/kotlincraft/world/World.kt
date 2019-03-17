package kotlincraft.world

import kotlincraft.Camera
import kotlincraft.Window
import kotlincraft.math.Vector
import kotlincraft.world.entity.Player

class World(window: Window) {

    companion object {
        const val width: Int = 10000 / Chunk.size
        const val height: Int = 500 / Chunk.size
    }

    private val chunks: Array<Array<Chunk?>>
    private val camera: Camera = Camera(window, 10.0, Vector.zero)

    private val player = Player(Vector.zero, Vector(0.8, 1.75))

    init {
        chunks = Array(height) { kotlin.arrayOfNulls<Chunk>(width) }
    }

    fun update(delta: Double) {
        player.update(delta)

        camera.position = player.position.add(Vector(player.size.x / 2, player.size.y / 2))

        camera.update()
    }

    fun render() {
        camera.setProjection()

        val xChunks = (camera.viewportWidth / Chunk.size).toInt() + 2
        for (y in -1..1) {
            for (x in -xChunks / 2..xChunks / 2) {
                renderRelativeChunk(Vector(x, y))
            }
        }

        player.render()
    }

    private fun getChunk(position: Vector): Chunk? {
        if (!isChunkInRange(position)) return null
        val x = (position.x / Chunk.size).toInt()
        val y = (position.y / Chunk.size).toInt()
        var chunk = chunks[x][y]
        if (chunk == null) {
            chunk = Chunk(Vector(x * Chunk.size, y * Chunk.size))
            chunks[x][y] = chunk
        }
        return chunk
    }

    private fun renderRelativeChunk(relativeIndex: Vector) {
        getChunk(camera.position.add(relativeIndex.multiply(Chunk.size.toDouble())))?.render()
    }

    private fun isChunkInRange(position: Vector): Boolean {
        return !(position.x < 0 || position.y < 0 || position.x >= width * Chunk.size || position.y >= height * Chunk.size)
    }

    fun dispose() {
        camera.dispose()
    }
}
