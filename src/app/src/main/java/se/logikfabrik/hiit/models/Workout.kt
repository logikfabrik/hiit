package se.logikfabrik.hiit.models

data class Workout(
    val workTimeMillis: Long,
    val restTimeMillis: Long,
    val numberOfSets: Long,
    val setTimeMillis: Long = workTimeMillis + restTimeMillis,
    val timeMillis: Long = (setTimeMillis * numberOfSets) - restTimeMillis
) {
    init {
        require(workTimeMillis > 0)
        require(restTimeMillis >= 0)
        require(numberOfSets >= 1)
    }

    enum class Stage {
        WORK,
        REST
    }
}