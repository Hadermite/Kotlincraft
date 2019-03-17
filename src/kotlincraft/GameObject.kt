package kotlincraft

import kotlincraft.math.Vector

abstract class GameObject(open var position: Vector, open var size: Vector) {

    abstract fun render()
}
