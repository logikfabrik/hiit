package se.logikfabrik.hiit.events

import se.logikfabrik.hiit.models.Workout

class WorkoutStoppedEvent(val workout: Workout, val totalTimeElapsed: Int) {
}