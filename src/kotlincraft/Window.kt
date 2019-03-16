package kotlincraft

import kotlincraft.input.Input
import kotlincraft.math.Vector
import kotlincraft.world.World
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL15.*

class Window {

    var size = Vector.zero; private set
    var aspectRatio = 0.0; private set
    var fps = 0; private set
    var ups = 0; private set
    private var window: Long = 0
    private var fullscreen = false
    private var vSync = false

    private val resizeListeners = ArrayList<ResizeListener>()

    init {
        if (!glfwInit()) {
            error("Failed to initialize GLFW!")
        } else {
            createWindow(0)
        }
    }

    private fun createWindow(oldWindow: Long) {
        val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor()) ?: return

        size = Vector(vidMode.width(), vidMode.height())
        if (!fullscreen) size = size.divide(2.0)

        window = glfwCreateWindow(
            size.x.toInt(),
            size.y.toInt(),
            "Kotlincraft",
            if (fullscreen) glfwGetPrimaryMonitor() else 0,
            oldWindow
        )

        glfwSetWindowPos(
            window,
            (vidMode.width() - size.x.toInt()) / 2,
            (vidMode.height() - size.y.toInt()) / 2
        )

        glfwSetWindowSizeCallback(window) { _, w, h -> onResize(w, h) }
        glfwSetKeyCallback(window, Input.keyboard)
        glfwSetMouseButtonCallback(window, Input.mouse)
        glfwSetCursorPosCallback(window, Input.cursor)

        glfwMakeContextCurrent(window)
        glfwSwapInterval(if (vSync) 1 else 0)

        GL.createCapabilities()

        glClearColor(0f, 0f, 0f, 0f)

        glEnable(GL_TEXTURE_2D)
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        onResize(size.x.toInt(), size.y.toInt())
    }

    fun start() {
        val world = World(this)
        val updateInterval = 1.0 / Constants.tickRate
        var timeSinceLastUpdate = 0.0
        var lastTime = System.nanoTime()

        val fpsTimer = CounterTimer(1.0) { count -> fps = count }
        val upsTimer = CounterTimer(1.0) { count -> ups = count }

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT)

            val now = System.nanoTime()
            val delta = (now - lastTime) / 1000000000.0
            timeSinceLastUpdate += delta
            lastTime = now

            Input.update()

            if (timeSinceLastUpdate >= updateInterval) {
                timeSinceLastUpdate -= updateInterval
                world.update(updateInterval)
                upsTimer.add()
            }

            world.render()
            fpsTimer.add()

            upsTimer.update(delta)
            fpsTimer.update(delta)

            glfwSwapBuffers(window)

            glfwPollEvents()
        }

        glfwDestroyWindow(window)
        glfwTerminate()
    }

    fun addResizeListener(resizeListener: ResizeListener) {
        resizeListeners.add(resizeListener)
    }

    fun removeResizeListener(resizeListener: ResizeListener) {
        resizeListeners.remove(resizeListener)
    }

    private fun onResize(width: Int, height: Int) {
        size = Vector(width.toDouble(), height.toDouble())
        aspectRatio = width.toDouble() / height
        resizeListeners.forEach { l -> l.onWindowResize(size) }
    }

    interface ResizeListener {
        fun onWindowResize(size: Vector)
    }
}
