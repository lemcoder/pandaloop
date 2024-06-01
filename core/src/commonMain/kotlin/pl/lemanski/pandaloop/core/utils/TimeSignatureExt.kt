package pl.lemanski.pandaloop.core.utils

import pl.lemanski.pandaloop.core.internal.engine.DefaultAudioEngineOptions
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.internal.engine.getBytesPerFrame
import kotlin.math.round
import kotlin.time.Duration.Companion.minutes

/**
 * Get time in milliseconds for given time signature and tempo
 * @param tempo tempo to use for calculation
 * @return time in milliseconds
 */

fun TimeSignature.getTimeWithTempo(tempo: Int): Double {
    val beat = 1.minutes.inWholeMilliseconds.toDouble() / tempo
    return when (this) {
        TimeSignature.COMMON      -> (4.0 * beat)
        TimeSignature.THREE_FOURS -> (3.0 * beat)
    }
}