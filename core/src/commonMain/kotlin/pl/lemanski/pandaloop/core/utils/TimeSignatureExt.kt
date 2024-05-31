package pl.lemanski.pandaloop.core.utils

import pl.lemanski.pandaloop.core.PandaLoopContext
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.core.engine.getBytesPerFrame
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

/**
 * Get buffer size in bytes for given time signature and tempo
 * @param tempo tempo to use for calculation
 * @param bytesPerFrame number of bytes per frame (internal use for testing)
 *
 * @return buffer size in bytes
 */
fun TimeSignature.getBufferSizeInBytesWithTempo(tempo: Int, bytesPerFrame: Int = getBytesPerFrame()): Long {
    val timeInMs = getTimeWithTempo(tempo)

    val bufferSize = round(
        PandaLoopContext.sampleRate.toDouble() * PandaLoopContext.channelCount * bytesPerFrame * (timeInMs / 1000.0)
    ).toLong()

    return bufferSize
}