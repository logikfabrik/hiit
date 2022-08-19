package se.logikfabrik.hiit.models

import org.junit.Assert.*
import org.junit.Test

class WorkoutExtensionsTest {
    @Test
    fun getNumberOfSetsElapsed_Should_ReturnNumberOfSetsElapsed() {
        val sut = Workout(45, 10, 15)

        val numberOfSetsElapsed = sut.getNumberOfSetsElapsed(810)

        assertEquals(14, numberOfSetsElapsed)
    }

    @Test
    fun getSetTimeElapsedInSeconds_Should_ReturnSetTimeElapsedInSeconds() {
        val sut = Workout(45, 10, 15)

        val setTimeElapsedInSeconds = sut.getSetTimeElapsedInSeconds(101)

        assertEquals(46, setTimeElapsedInSeconds)
    }
}