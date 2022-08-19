package se.logikfabrik.hiit.events

import se.logikfabrik.hiit.models.Workout

class WorkoutStartedEvent(val workout: Workout, val totalTimeElapsed: Int? = null) {
    init {
        require(totalTimeElapsed ?: 0 >= 0)
    }
}