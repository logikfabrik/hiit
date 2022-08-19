package se.logikfabrik.hiit.events

import se.logikfabrik.hiit.models.Workout

data class WorkoutPausedEvent(val workout: Workout, val totalTimeElapsed: Int) {
    init {
        require(totalTimeElapsed >= 0)
    }
}