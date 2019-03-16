package kotlincraft.graphics

import kotlincraft.math.Vector
import org.lwjgl.opengl.GL15.*
import org.lwjgl.stb.STBImage.*
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

class Texture(path: String, nearestFilter: Boolean = false) {

    private val textureId: Int = glGenTextures()

    var width: Int = 0; private set
    var height: Int = 0; private set
    var aspectRatio: Double = 0.0; private set

    init {
        loadTexture(path, nearestFilter)
    }

    fun render(position: Vector, size: Vector, color: Color = Color.white) {
        bind()
        var width = size.x
        var height = size.y
        if (width == Double.MAX_VALUE && height == Double.MAX_VALUE) {
            width = this.width.toDouble()
            height = this.height.toDouble()
        } else if (width == Double.MAX_VALUE) {
            width = height * this.aspectRatio
        } else if (height == Double.MAX_VALUE) {
            height = width / this.aspectRatio
        }

        glBegin(GL_QUADS)
        glColor4d(color.red, color.green, color.blue, color.alpha)

        glTexCoord2i(0, 0)
        glVertex2d(position.x, position.y)

        glTexCoord2i(1, 0)
        glVertex2d(position.x + width, position.y)

        glTexCoord2i(1, 1)
        glVertex2d(position.x + width, position.y + height)

        glTexCoord2i(0, 1)
        glVertex2d(position.x, position.y + height)

        glEnd()
        unbind()
    }

    private fun loadTexture(path: String, nearestFilter: Boolean) {
        val stack = MemoryStack.stackPush() ?: return

        val w = stack.mallocInt(1)
        val h = stack.mallocInt(1)
        val comp = stack.mallocInt(1)

        stbi_set_flip_vertically_on_load(true)
        val buffer = stbi_load(path, w, h, comp, 4)
        if (buffer == null) {
            System.err.println("Failed to load texture file: $path")
            return
        }

        width = w.get()
        height = h.get()
        aspectRatio = width.toDouble() / height

        bind()
        setParameters(nearestFilter)
        uploadData(GL_RGBA8, width, height, GL_RGBA, buffer)
    }

    private fun setParameters(nearestFilter: Boolean) {
        setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER)
        setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER)
        setParameter(GL_TEXTURE_MIN_FILTER, if (nearestFilter) GL_NEAREST else GL_LINEAR)
        setParameter(GL_TEXTURE_MAG_FILTER, if (nearestFilter) GL_NEAREST else GL_LINEAR)
    }

    private fun bind() {
        glBindTexture(GL_TEXTURE_2D, textureId)
    }

    private fun unbind() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    private fun uploadData(internalFormat: Int, width: Int, height: Int, format: Int, data: ByteBuffer) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data)
    }

    private fun setParameter(name: Int, value: Int) {
        glTexParameteri(GL_TEXTURE_2D, name, value)
    }

    private fun delete() {
        glDeleteTextures(textureId)
    }
}
