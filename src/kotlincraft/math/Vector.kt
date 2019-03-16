package kotlincraft.math

data class Vector(val x: Double, val y: Double) {

    companion object {
        val zero = Vector(0.0, 0.0)
        val one = Vector(1.0, 1.0)
    }

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    fun add(other: Vector): Vector {
        return Vector(x + other.x, y + other.y)
    }

    fun substract(other: Vector): Vector {
        return Vector(x - other.x, y - other.y)
    }

    fun multiply(scalar: Double): Vector {
        return Vector(x * scalar, y * scalar)
    }

    fun divide(scalar: Double): Vector {
        return Vector(x / scalar, y / scalar)
    }
}
