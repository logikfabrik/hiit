package se.logikfabrik.hiit.models

import org.junit.Assert.*
import org.junit.Test

class WorkoutExtensionsTest {
    @Test
    fun getElapsedNumberOfSets_Should_ReturnElapsedNumberOfSets() {
        val sut = Workout(45000, 10000, 15)

        val elapsedNumberOfSets = sut.getElapsedNumberOfSets(810000)

        assertEquals(14, elapsedNumberOfSets)
    }

    @Test
    fun getElapsedSetTimeMillis_Should_ReturnElapsedSetTimeMillis() {
        val sut = Workout(45000, 10000, 15)

        val elapsedSetTimeMillis = sut.getElapsedSetTimeMillis(101000)

        assertEquals(46000, elapsedSetTimeMillis)
    }

    @Test
    fun getStage_Should_ReturnStage() {
        val sut = Workout(45000, 10000, 15)

        val stage = sut.getStage(155000)

        assertEquals(Workout.Stage.REST, stage)
    }

    @Test
    fun getStageTimeMillis_Should_ReturnStageTimeMillis() {
        val sut = Workout(45000, 10000, 15)

        val stageTimeMillis = sut.getStageTimeMillis(46000)

        assertEquals(10000, stageTimeMillis)
    }
}