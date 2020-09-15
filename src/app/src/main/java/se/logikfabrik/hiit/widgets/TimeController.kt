package se.logikfabrik.hiit.widgets

interface TimeController {

    val isRunning: Boolean

    val isPaused: Boolean

    val isStopped: Boolean

    fun run()

    fun pause()

    fun stop()
}
