package se.logikfabrik.hiit.models

data class Workout(
    val workInSeconds: Int,
    val restInSeconds: Int,
    val numberOfSets: Int,
    val timeInSeconds: Int = ((workInSeconds + restInSeconds) * numberOfSets) - restInSeconds
) {
    init {
        require(workInSeconds > 0)
        require(restInSeconds >= 0)
        require(numberOfSets >= 1)
    }
}