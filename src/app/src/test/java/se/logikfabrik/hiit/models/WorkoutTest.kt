package se.logikfabrik.hiit.models

import org.junit.Assert.*
import org.junit.Test

class WorkoutTest {
    @Test
    fun workInSeconds_Should_ReturnWorkInSeconds() {
        val sut = Workout(45, 10, 15)

        val workInSeconds = sut.workInSeconds

        assertEquals(45, workInSeconds)
    }

    @Test
    fun restInSeconds_Should_ReturnRestInSeconds() {
        val sut = Workout(45, 10, 15)

        val restInSeconds = sut.restInSeconds

        assertEquals(10, restInSeconds)
    }

    @Test
    fun numberOfSets_Should_ReturnNumberOfSets() {
        val sut = Workout(45, 10, 15)

        val numberOfSets = sut.numberOfSets

        assertEquals(15, numberOfSets)
    }

    @Test
    fun timeInSeconds_Should_ReturnTimeInSeconds() {
        val sut = Workout(45, 10, 15)

        val timeInSeconds = sut.timeInSeconds

        assertEquals(815, timeInSeconds)
    }
}