package pl.lemanski.pandaloop.core.utils

import pl.lemanski.pandaloop.core.TimeSignature
import kotlin.time.Duration.Companion.minutes

internal fun TimeSignature.getTimeWithTempo(tempo: Int): Double {
    val beat = 1.minutes.inWholeMilliseconds.toDouble() / tempo
    return when (this) {
        TimeSignature.COMMON      -> (4.0 * beat)
        TimeSignature.THREE_FOURS -> (3.0 * beat)
    }
}

/**
 * Get time in milliseconds for given time signature and tempo
 *
 * @param tempo tempo to use for calculation
 * @param measures number of measures to use for calculation
 *
 * @return time in milliseconds
 */
fun TimeSignature.getTime(tempo: Int, measures: Int = 1): Long = (this.getTimeWithTempo(tempo) * measures).toLong().coerceAtLeast(0)
