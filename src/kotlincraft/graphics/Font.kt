package kotlincraft.graphics

import kotlincraft.math.Vector
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Exception
import java.util.HashMap

class Font private constructor(awtFont: java.awt.Font, private val antiAliased: Boolean) {

    private val characters = HashMap<Char, Texture>()
    private val font: java.awt.Font = awtFont.deriveFont(300f)
    private val fontMetrics: FontMetrics

    companion object {
        fun createFont(path: String, antiAliased: Boolean): Font? {
            try {
                val awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, File(path)) ?: return null
                return Font(awtFont, antiAliased)
            } catch (_: Exception) {
                System.err.println("Failed to load AWT font: $path")
                return null
            }
        }
    }

    init {
        val tempImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val g = tempImage.graphics as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.font = font
        fontMetrics = g.fontMetrics
    }

    fun render(text: String, height: Double, color: Color, position: Vector) {
        var currentX = 0.0
        for (i in 0 until text.length) {
            val character = text[i]
            val texture = getCharacterTexture(character)
            val width = getCharacterWidth(character, height)
            texture.render(Vector(currentX + position.x, position.y), Vector(width, height), color)
            currentX += width
        }
    }

    fun getCharacterWidth(character: Char, height: Double): Double {
        val texture = getCharacterTexture(character)
        return height * texture.aspectRatio
    }

    fun getStringWidth(text: String, height: Double): Double {
        var width = 0.0
        for (c in text.toCharArray()) {
            width += getCharacterWidth(c, height)
        }
        return width
    }

    private fun getCharacterTexture(character: Char): Texture {
        val characterTexture = characters[character]
        if (characterTexture != null) return characterTexture

        var charWidth = fontMetrics.charWidth(character)

        if (charWidth <= 0) {
            System.err.println("Character width of $character is 0 or less!")
            charWidth = 0
        }
        var charHeight = fontMetrics.height
        if (charHeight <= 0) {
            System.err.println("Character height of $character is 0 or less!")
            charHeight = 0
        }

        val fontImage = BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB)
        val g = fontImage.graphics as Graphics2D
        if (antiAliased)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        g.font = font
        g.color = java.awt.Color.WHITE
        g.drawString(character.toString(), 0, fontMetrics.ascent)

        val texture = Texture(fontImage, false)
        characters[character] = texture
        return texture
    }
}