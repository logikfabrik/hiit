package se.logikfabrik.hiit.models

fun Workout.getNumberOfSetsElapsed(timeElapsedInSeconds: Int): Int {
    require(timeElapsedInSeconds <= timeInSeconds)

    return ((timeElapsedInSeconds.toFloat() / ((workInSeconds + restInSeconds) * numberOfSets)) * numberOfSets).toInt()
}

fun Workout.getSetTimeElapsedInSeconds(timeElapsedInSeconds: Int): Int {
    require(timeElapsedInSeconds <= timeInSeconds)

    return timeElapsedInSeconds - ((workInSeconds + restInSeconds) * getNumberOfSetsElapsed(timeElapsedInSeconds))
}