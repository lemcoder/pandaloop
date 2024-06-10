package pl.lemanski.pandaloop.core.utils

import pl.lemanski.pandaloop.core.PandaLoop
import pl.lemanski.pandaloop.core.TimeSignature
import kotlin.math.round

internal fun PandaLoop.getBufferSizeInBytesWithTempo(timeSignature: TimeSignature, tempo: Int): Long {
    val timeInMs = timeSignature.getTimeWithTempo(tempo)

    val bufferSize = round(
        engine.options.sampleRate.toDouble() * engine.options.channelCount * engine.options.bytesPerFrame * (timeInMs / 1000.0)
    ).toLong()

    return bufferSize
}

/**
 * Get empty buffer for given time signature and tempo
 *
 * @param timeSignature time signature to use for calculation
 * @param tempo tempo to use for calculation
 * @param measures number of measures to use for calculation
 *
 * @return empty buffer of given size
 */
fun PandaLoop.emptyBuffer(timeSignature: TimeSignature, tempo: Int, measures: Int = 1): ByteArray = ByteArray(this.getBufferSizeInBytesWithTempo(timeSignature, tempo).toInt().coerceAtLeast(0) * measures)

/**
 * Get buffer size in bytes for given time signature, tempo and number of measures
 *
 * @param timeSignature time signature to use for calculation
 * @param tempo tempo to use for calculation
 * @param measures number of measures to use for calculation
 *
 * @return buffer size in bytes
 */
fun PandaLoop.getBufferSizeInBytes(timeSignature: TimeSignature, tempo: Int, measures: Int = 1): Long = this.getBufferSizeInBytesWithTempo(timeSignature, tempo).coerceAtLeast(0) * measures