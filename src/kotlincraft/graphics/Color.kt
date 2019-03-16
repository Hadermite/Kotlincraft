package kotlincraft.graphics

data class Color(val red: Double, val green: Double, val blue: Double, val alpha: Double = 1.0) {

    companion object {
        val black = Color(0.0, 0.0, 0.0)
        val white = Color(1.0, 1.0, 1.0)

        val red = Color(1.0, 0.0, 0.0)
        val green = Color(0.0, 1.0, 0.0)
        val blue = Color(0.0, 0.0, 1.0)
    }
}
