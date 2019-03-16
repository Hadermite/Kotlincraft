package kotlincraft.input

object Input {

    val keyboard = Keyboard()
    val mouse = Mouse()
    val cursor = Cursor()

    fun update() {
        mouse.update()
    }
}
