package se.logikfabrik.hiit.models

fun Workout.getElapsedNumberOfSets(elapsedTimeMillis: Long): Long {
    require(elapsedTimeMillis >= 0)
    require(elapsedTimeMillis <= timeMillis)

    return ((elapsedTimeMillis.toFloat() / ((setTimeMillis) * numberOfSets)) * numberOfSets).toLong()
}

fun Workout.getElapsedSetTimeMillis(elapsedTimeMillis: Long): Long {
    require(elapsedTimeMillis >= 0)
    require(elapsedTimeMillis <= timeMillis)

    return elapsedTimeMillis % setTimeMillis
}

fun Workout.getStage(elapsedTimeMillis: Long): Workout.Stage {
    require(elapsedTimeMillis >= 0)
    require(elapsedTimeMillis <= timeMillis)

    return if (getElapsedSetTimeMillis(elapsedTimeMillis) < workTimeMillis) {
        Workout.Stage.WORK
    } else {
        Workout.Stage.REST
    }
}

fun Workout.getStageTimeMillis(elapsedTimeMillis: Long): Long {
    require(elapsedTimeMillis >= 0)
    require(elapsedTimeMillis <= timeMillis)

    return when (getStage(elapsedTimeMillis)) {
        Workout.Stage.WORK -> workTimeMillis
        Workout.Stage.REST -> restTimeMillis
    }
}

fun Workout.getElapsedStageTimeMillis(elapsedTimeMillis: Long): Long {
    require(elapsedTimeMillis <= timeMillis)

    val elapsedSetTimeMillis = getElapsedSetTimeMillis(elapsedTimeMillis);

    return when (getStage(elapsedTimeMillis)) {
        Workout.Stage.WORK -> elapsedSetTimeMillis
        Workout.Stage.REST -> elapsedSetTimeMillis - workTimeMillis
    }
}