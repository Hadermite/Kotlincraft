package kotlincraft

class CounterTimer(val time: Double, val callback: (count: Int) -> Unit) {

    private var timer = time
    private var count = 0

    fun update(delta: Double) {
        timer -= delta
        if (timer > 0) return
        timer = time
        callback(count)
        count = 0
    }

    fun add() {
        count++
    }
}
