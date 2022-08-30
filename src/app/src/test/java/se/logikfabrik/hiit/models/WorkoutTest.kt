package se.logikfabrik.hiit.models

import org.junit.Assert.*
import org.junit.Test

class WorkoutTest {
    @Test
    fun workTimeMillis_Should_ReturnWorkTimeMillis() {
        val sut = Workout(45000, 10000, 15)

        val workTimeMillis = sut.workTimeMillis

        assertEquals(45000, workTimeMillis)
    }

    @Test
    fun restTimeMillis_Should_ReturnRestTimeMillis() {
        val sut = Workout(45000, 10000, 15)

        val restTimeMillis = sut.restTimeMillis

        assertEquals(10000, restTimeMillis)
    }

    @Test
    fun numberOfSets_Should_ReturnNumberOfSets() {
        val sut = Workout(45000, 10000, 15)

        val numberOfSets = sut.numberOfSets

        assertEquals(15, numberOfSets)
    }

    @Test
    fun setTimeMillis_Should_ReturnSetTimeMillis() {
        val sut = Workout(45000, 10000, 15)

        val setTimeMillis = sut.setTimeMillis

        assertEquals(55000, setTimeMillis)
    }

    @Test
    fun timeMillis_Should_ReturnTimeMillis() {
        val sut = Workout(45000, 10000, 15)

        val timeMillis = sut.timeMillis

        assertEquals(815000, timeMillis)
    }
}